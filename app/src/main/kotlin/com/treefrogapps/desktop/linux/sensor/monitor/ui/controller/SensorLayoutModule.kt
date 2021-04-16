package com.treefrogapps.desktop.linux.sensor.monitor.ui.controller

import com.treefrogapps.desktop.linux.sensor.monitor.ui.adapter.SensorListItem
import com.treefrogapps.desktop.linux.sensor.monitor.ui.adapter.SensorListItemCellFactory
import com.treefrogapps.javafx.dagger.ControllerFactory
import com.treefrogapps.javafx.dagger.Scopes.ControllerScope
import dagger.Binds
import dagger.Module
import dagger.Provides
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.util.Callback
import javax.inject.Provider

@Module abstract class SensorLayoutModule {

    companion object {
        @Provides
        @ControllerScope
        fun factory(controller: Provider<SensorLayoutController>): ControllerFactory<SensorLayoutController> =
            object : ControllerFactory<SensorLayoutController> {
                override fun layout(): String = "sensor_layout"
                override fun callback(): Callback<Class<SensorLayoutController>, SensorLayoutController> =
                    Callback<Class<SensorLayoutController>, SensorLayoutController> { controller.get() }
            }
    }

    @Binds
    @ControllerScope
    abstract fun cpuCellItemFactory(factory : SensorListItemCellFactory) : Callback<ListView<SensorListItem>, ListCell<SensorListItem>>

}