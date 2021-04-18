package com.treefrogapps.desktop.linux.sensor.monitor.ui.adapter

import com.treefrogapps.javafx.LayoutInflater
import com.treefrogapps.javafx.dagger.Scopes.ControllerScope
import com.treefrogapps.kotlin.core.extensions.orElse
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.scene.layout.HBox
import javafx.util.Callback
import javax.inject.Inject

@ControllerScope class SensorListItemCellFactory @Inject constructor(
    private val inflater: LayoutInflater,
    private val controllerFactory: SensorListItemController.Factory)
    : Callback<ListView<SensorListItem>, ListCell<SensorListItem>> {

    override fun call(param: ListView<SensorListItem>?): SensorListItemCell = SensorListItemCell(controller())

    private fun controller(): SensorListItemController =
        SensorListItemCellFactory::class.java.getResource("/fxml/sensor_item_layout.fxml")
            ?.run { inflater.inflate<SensorListItemController, HBox>(this, controllerFactory) }
            .orElse { throw IllegalStateException("Unable to create controller") }
}