package com.treefrogapps.desktop.linux.sensor.monitor

import com.treefrogapps.desktop.linux.sensor.monitor.ui.stage.SensorLayoutStageModule
import com.treefrogapps.javafx.dagger.ApplicationInjector
import dagger.BindsInstance
import dagger.Component
import javafx.application.Application
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        SensorMonitorAppModule::class,
        SensorLayoutStageModule::class
    ])
interface SensorMonitorAppComponent : ApplicationInjector<SensorMonitorApp> {

    @Component.Factory interface Factory {

        fun addApp(@BindsInstance app: Application): ApplicationInjector<SensorMonitorApp>
    }
}