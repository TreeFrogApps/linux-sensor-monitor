package com.treefrogapps.desktop.linux.sensor.monitor.ui.controllers

import com.treefrogapps.javafx.dagger.ControllerFactory
import com.treefrogapps.javafx.dagger.Scopes.ControllerScope
import dagger.Module
import dagger.Provides
import javafx.util.Callback
import javax.inject.Provider

@Module object SensorLayoutModule {

    @Provides
    @ControllerScope
    fun factory(controller: Provider<SensorLayoutController>): ControllerFactory<SensorLayoutController> =
        object : ControllerFactory<SensorLayoutController> {
            override fun layout(): String = "sensor_layout"
            override fun callback(): Callback<Class<SensorLayoutController>, SensorLayoutController> =
                Callback<Class<SensorLayoutController>, SensorLayoutController> { controller.get() }
        }
}