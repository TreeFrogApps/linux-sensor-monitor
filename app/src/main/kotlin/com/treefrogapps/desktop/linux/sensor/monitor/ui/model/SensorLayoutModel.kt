package com.treefrogapps.desktop.linux.sensor.monitor.ui.model

import com.treefrogapps.desktop.linux.sensor.monitor.data.SensorData
import com.treefrogapps.desktop.linux.sensor.monitor.data.SensorData.DeviceType.CPU
import com.treefrogapps.desktop.linux.sensor.monitor.data.SensorDataFactory
import com.treefrogapps.desktop.linux.sensor.monitor.repository.SettingsRepository
import com.treefrogapps.desktop.linux.sensor.monitor.repository.SettingsRepository.Companion.CPU_UPDATE_INTERVAL_KEY
import com.treefrogapps.desktop.linux.sensor.monitor.repository.SettingsRepository.Companion.OTHER_UPDATE_INTERVAL_KEY
import com.treefrogapps.javafx.dagger.Scopes.ControllerScope
import io.reactivex.rxjava3.core.Flowable
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Inject

@ControllerScope class SensorLayoutModel @Inject constructor(private val repository: SettingsRepository) {


    fun updateCpuPollRate(periodSeconds: Int) {

    }

    fun observeCpuData(): Flowable<SensorData.Device> =
        sensorFlowable(CPU_UPDATE_INTERVAL_KEY)
            .filter { ds -> ds.devices.map { it.type }.contains(CPU) }
            .map { ds -> ds.devices.first { it.type == CPU } }


    fun updateOtherPollRate(periodSeconds: Int) {

    }

    fun observeSensorData(): Flowable<List<SensorData.Device>> =
        sensorFlowable(OTHER_UPDATE_INTERVAL_KEY)
            .map { ds -> ds.devices.filter { it.type != CPU } }

    private fun sensorFlowable(periodRateKey: String): Flowable<SensorData> =
        repository.observeIntSetting(periodRateKey)
            .map(Int::toLong)
            .distinctUntilChanged().switchMap { periodRate ->
                Flowable.interval(0, periodRate, SECONDS)
                    .flatMap { Flowable.fromCallable { SensorDataFactory().get() } }
            }

}