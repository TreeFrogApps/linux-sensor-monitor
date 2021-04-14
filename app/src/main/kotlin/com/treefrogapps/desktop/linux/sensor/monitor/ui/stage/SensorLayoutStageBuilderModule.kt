package com.treefrogapps.desktop.linux.sensor.monitor.ui.stage

import com.treefrogapps.desktop.linux.sensor.monitor.ui.controllers.SensorLayoutSubcomponent
import com.treefrogapps.desktop.linux.sensor.monitor.ui.controllers.SensorLayoutModule
import com.treefrogapps.javafx.LayoutStage
import com.treefrogapps.javafx.LayoutStageManager.StageKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(
    includes = [SensorLayoutModule::class],
    subcomponents = [SensorLayoutSubcomponent::class])
@Suppress("UNCHECKED_CAST")
abstract class SensorLayoutStageBuilderModule {

    @Binds
    @IntoMap
    @StageKey(value = SensorLayoutStage::class)
    abstract fun sensorStage(layoutStage: SensorLayoutStage): LayoutStage

}