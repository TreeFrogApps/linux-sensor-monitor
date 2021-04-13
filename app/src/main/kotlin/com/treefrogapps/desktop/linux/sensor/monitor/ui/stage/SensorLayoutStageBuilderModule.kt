package com.treefrogapps.desktop.linux.sensor.monitor.ui.stage

import com.treefrogapps.desktop.linux.sensor.monitor.ui.controllers.SensorLayoutController
import com.treefrogapps.desktop.linux.sensor.monitor.ui.controllers.SensorLayoutControllerModule
import com.treefrogapps.desktop.linux.sensor.monitor.ui.stage.SensorLayoutStageBuilderModule.StageModule
import com.treefrogapps.desktop.linux.sensor.monitor.ui.stage.SensorLayoutStageBuilderModule.StageModule.ControllersModule
import com.treefrogapps.desktop.linux.sensor.monitor.ui.stage.SensorLayoutStageBuilderModule.StageModule.LayoutsModule
import com.treefrogapps.javafx.LayoutStage
import com.treefrogapps.javafx.LayoutStage.ControllerKey
import com.treefrogapps.javafx.LayoutStageManager.StageKey
import com.treefrogapps.javafx.dagger.DaggerInitializable
import com.treefrogapps.javafx.dagger.InitializableComponentBuilder
import com.treefrogapps.javafx.dagger.InitializableComponentInjector
import com.treefrogapps.javafx.dagger.InitializableInjector.InitializableKey
import com.treefrogapps.javafx.dagger.Scopes.InitializableScope
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import dagger.multibindings.IntoMap

@Module(
    includes = [StageModule::class],
    subcomponents = [StageModule.SensorLayoutControllerSubcomponent::class])
@Suppress("UNCHECKED_CAST")
abstract class SensorLayoutStageBuilderModule {

    @Binds
    @IntoMap
    @StageKey(value = SensorLayoutStage::class)
    abstract fun sensorStage(layoutStage: SensorLayoutStage): LayoutStage

    @Module(includes = [LayoutsModule::class, ControllersModule::class])
    abstract class StageModule {

        @Module object LayoutsModule {

            @Provides
            @IntoMap
            @ControllerKey(value = SensorLayoutController::class)
            fun inCallLayout(): String = "sensors_main"
        }

        @Module object ControllersModule {

            @Provides
            @IntoMap
            @InitializableKey(value = SensorLayoutController::class)
            fun sensorController(builder: SensorLayoutControllerSubcomponent.Builder): InitializableComponentBuilder<DaggerInitializable> {
                return builder as InitializableComponentBuilder<in DaggerInitializable>
            }
        }

        @InitializableScope
        @Subcomponent(modules = [SensorLayoutControllerModule::class])
        interface SensorLayoutControllerSubcomponent : InitializableComponentInjector<SensorLayoutController> {
            @Subcomponent.Builder
            abstract class Builder : InitializableComponentBuilder<SensorLayoutController>()
        }
    }
}