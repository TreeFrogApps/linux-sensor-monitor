package com.treefrogapps.desktop.linux.sensor.monitor

import com.treefrogapps.desktop.linux.sensor.monitor.ui.stage.SensorLayoutStageBuilderModule
import com.treefrogapps.javafx.dagger.ApplicationInjector
import dagger.BindsInstance
import dagger.Component
import javafx.application.Application
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        SensorMonitorAppModule::class,
        SensorLayoutStageBuilderModule::class
    ])
interface SensorMonitorAppComponent : ApplicationInjector<SensorMonitorApp> {

    @Component.Factory interface Factory {

        fun addApp(@BindsInstance app: Application): ApplicationInjector<SensorMonitorApp>
    }
}