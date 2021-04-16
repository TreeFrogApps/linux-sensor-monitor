package com.treefrogapps.desktop.linux.sensor.monitor.ui.model

import com.treefrogapps.desktop.linux.sensor.monitor.data.SensorData
import com.treefrogapps.desktop.linux.sensor.monitor.repository.UserSettingsRepository
import com.treefrogapps.javafx.dagger.Scopes
import com.treefrogapps.javafx.dagger.Scopes.ControllerScope
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

@ControllerScope class OtherSensorLayoutModel @Inject constructor(private val repository: UserSettingsRepository){



    fun observeSensorDate() : Flowable<List<SensorData.Device>> = Flowable.empty()
}