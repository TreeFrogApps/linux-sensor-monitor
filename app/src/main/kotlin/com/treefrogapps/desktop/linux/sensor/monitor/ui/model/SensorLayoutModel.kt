package com.treefrogapps.desktop.linux.sensor.monitor.ui.model

import com.treefrogapps.desktop.linux.sensor.monitor.data.CommandDataFactory
import com.treefrogapps.desktop.linux.sensor.monitor.data.CpuFrequencyData
import com.treefrogapps.desktop.linux.sensor.monitor.data.SensorData
import com.treefrogapps.desktop.linux.sensor.monitor.data.SensorData.DeviceType.CPU
import com.treefrogapps.desktop.linux.sensor.monitor.media.AlertPlayer
import com.treefrogapps.desktop.linux.sensor.monitor.repository.SettingsRepository
import com.treefrogapps.desktop.linux.sensor.monitor.repository.SettingsRepository.Companion.CPU_UPDATE_INTERVAL_KEY
import com.treefrogapps.desktop.linux.sensor.monitor.repository.SettingsRepository.Companion.OTHER_UPDATE_INTERVAL_KEY
import com.treefrogapps.javafx.dagger.Scopes.ControllerScope
import io.reactivex.rxjava3.core.Flowable
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Inject

@ControllerScope class SensorLayoutModel @Inject constructor(
    private val repository: SettingsRepository,
    private val sensorDataFactory: CommandDataFactory<SensorData>,
    private val cpuFrequencyDataFactory: CommandDataFactory<CpuFrequencyData>,
    private val alertPlayer: AlertPlayer) {

    private val cpuDataObservable: Flowable<SensorData.Device> by lazy {
        sensorFlowable(CPU_UPDATE_INTERVAL_KEY)
            .filter { ds -> ds.devices.map { it.type }.contains(CPU) }
            .map { ds -> ds.devices.first { it.type == CPU } }
            .publish()
            .autoConnect()
    }

    private val sensorDataObservable: Flowable<List<SensorData.Device>> by lazy {
        sensorFlowable(OTHER_UPDATE_INTERVAL_KEY)
            .map { ds -> ds.devices.filter { it.type != CPU } }
            .publish()
            .autoConnect()
    }

    fun updateCpuPollRate(periodSeconds: Int) = repository.setSetting(CPU_UPDATE_INTERVAL_KEY, periodSeconds)

    fun cpuPollData(): SensorPollRateData = SensorPollRateData(current = repository.getSetting(CPU_UPDATE_INTERVAL_KEY))

    fun observeCpuData(): Flowable<SensorData.Device> = cpuDataObservable

    fun observeCpuFrequencyData(): Flowable<CpuFrequencyData> =
        dataFlowable(CPU_UPDATE_INTERVAL_KEY, Flowable.fromCallable { cpuFrequencyDataFactory.get() })

    fun updateOtherPollRate(periodSeconds: Int) = repository.setSetting(OTHER_UPDATE_INTERVAL_KEY, periodSeconds)

    fun otherPollData(): SensorPollRateData =
        SensorPollRateData(current = repository.getSetting(OTHER_UPDATE_INTERVAL_KEY))

    fun observeSensorData(): Flowable<List<SensorData.Device>> = sensorDataObservable

    fun playAlert() = alertPlayer.playAlert()

    private fun sensorFlowable(periodRateKey: String): Flowable<SensorData> =
        dataFlowable(periodRateKey, Flowable.fromCallable(sensorDataFactory::get))

    private fun <T> dataFlowable(periodRateKey: String, dataFlowable: Flowable<T>): Flowable<T> =
        repository.observeIntSetting(periodRateKey)
            .map(Int::toLong)
            .distinctUntilChanged()
            .switchMap { periodRate -> Flowable.interval(0, periodRate, SECONDS).flatMap { dataFlowable } }
}