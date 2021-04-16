package com.treefrogapps.desktop.linux.sensor.monitor.ui.model

import com.treefrogapps.desktop.linux.sensor.monitor.data.SensorData
import com.treefrogapps.desktop.linux.sensor.monitor.data.SensorDataFactory
import com.treefrogapps.desktop.linux.sensor.monitor.repository.UserSettingsRepository
import com.treefrogapps.javafx.dagger.Scopes.ControllerScope
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@ControllerScope class CpuSensorLayoutModel @Inject constructor(private val repository: UserSettingsRepository) {


    fun observeCpuData(): Flowable<SensorData.Device> =
        Flowable.interval(2, TimeUnit.SECONDS)
            .flatMap { Flowable.fromCallable { SensorDataFactory().get() } }
            .filter { ds -> ds.devices.map { it.type }.contains(SensorData.DeviceType.CPU) }
            .map { ds -> ds.devices.first { it.type == SensorData.DeviceType.CPU } }
}