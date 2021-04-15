package com.treefrogapps.desktop.linux.sensor.monitor.ui.stage

import com.treefrogapps.desktop.linux.sensor.monitor.ui.controllers.SensorLayoutBuilderModule
import com.treefrogapps.javafx.LayoutStage
import com.treefrogapps.javafx.LayoutStageManager.StageKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [SensorLayoutBuilderModule::class])
abstract class SensorLayoutStageModule {

    @Binds
    @IntoMap
    @StageKey(value = SensorLayoutStage::class)
    abstract fun sensorStage(layoutStage: SensorLayoutStage): LayoutStage

}