package com.treefrogapps.desktop.linux.sensor.monitor.ui.controllers

import com.treefrogapps.javafx.LayoutStage.ControllerKey
import com.treefrogapps.javafx.dagger.DaggerInitializable
import com.treefrogapps.javafx.dagger.InitializableComponentFactory
import com.treefrogapps.javafx.dagger.InitializableInjector.InitializableKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module object SensorLayoutModule {

    @Provides
    @IntoMap
    @ControllerKey(value = SensorLayoutController::class)
    fun inCallLayout(): String = "sensors_main"

    @Provides
    @IntoMap
    @InitializableKey(value = SensorLayoutController::class)
    @Suppress("UNCHECKED_CAST")
    fun sensorController(factory: SensorLayoutSubcomponent.Factory): InitializableComponentFactory<DaggerInitializable> {
        return factory as InitializableComponentFactory<in DaggerInitializable>
    }

}