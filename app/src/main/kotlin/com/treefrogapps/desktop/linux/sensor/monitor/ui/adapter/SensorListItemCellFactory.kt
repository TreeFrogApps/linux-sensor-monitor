package com.treefrogapps.desktop.linux.sensor.monitor.ui.adapter

import com.treefrogapps.javafx.LayoutInflater
import com.treefrogapps.javafx.dagger.Scopes.ControllerScope
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
        inflater.inflate<SensorListItemController, HBox>("sensor_item_layout", controllerFactory)
            ?: throw IllegalStateException("Unable to create controller")
}