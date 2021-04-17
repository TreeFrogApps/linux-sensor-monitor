package com.treefrogapps.desktop.linux.sensor.monitor.ui.controller

import com.treefrogapps.desktop.linux.sensor.monitor.ui.controller.SensorLayoutBuilderModule.SensorLayoutSubcomponent
import com.treefrogapps.javafx.dagger.ControllerComponent
import com.treefrogapps.javafx.dagger.ControllerKey
import com.treefrogapps.javafx.dagger.DaggerController
import com.treefrogapps.javafx.dagger.Scopes.ControllerScope
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.multibindings.IntoMap


@Module(subcomponents = [SensorLayoutSubcomponent::class])
abstract class SensorLayoutBuilderModule {

    @Binds
    @IntoMap
    @ControllerKey(value = SensorLayoutController::class)
    abstract fun factory(factory: SensorLayoutSubcomponent.Factory): ControllerComponent.Factory<out DaggerController>

    @ControllerScope
    @Subcomponent(modules = [SensorLayoutModule::class])
    interface SensorLayoutSubcomponent : ControllerComponent<SensorLayoutController> {

        @Subcomponent.Factory
        interface Factory : ControllerComponent.Factory<SensorLayoutController>
    }
}