package com.treefrogapps.desktop.linux.sensor.monitor

import com.treefrogapps.desktop.linux.sensor.monitor.ui.stage.SensorLayoutStageBuilderModule
import com.treefrogapps.javafx.dagger.ApplicationInjector
import dagger.BindsInstance
import dagger.Component
import javafx.application.Application
import javax.inject.Singleton

@Singleton
@Component(modules = [
    SensorMonitorAppModule::class,
    SensorLayoutStageBuilderModule::class
])
interface SensorMonitorAppComponent : ApplicationInjector<SensorMonitorApp> {

    @Component.Builder interface Builder {

        @BindsInstance fun addApp(app: Application): Builder

        fun build(): SensorMonitorAppComponent
    }
}