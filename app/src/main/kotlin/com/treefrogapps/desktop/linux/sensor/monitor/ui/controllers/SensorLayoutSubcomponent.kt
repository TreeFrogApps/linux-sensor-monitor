package com.treefrogapps.desktop.linux.sensor.monitor.ui.controllers

import com.treefrogapps.javafx.dagger.InitializableComponentFactory
import com.treefrogapps.javafx.dagger.InitializableComponentInjector
import com.treefrogapps.javafx.dagger.Scopes
import com.treefrogapps.javafx.dagger.Scopes.InitializableScope
import dagger.Subcomponent

@InitializableScope
@Subcomponent(modules = [SensorLayoutControllerModule::class])
interface SensorLayoutSubcomponent : InitializableComponentInjector<SensorLayoutController> {
    @Subcomponent.Factory
    interface Factory : InitializableComponentFactory<SensorLayoutController>
}