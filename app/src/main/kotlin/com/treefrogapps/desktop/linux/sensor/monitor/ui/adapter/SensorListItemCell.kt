package com.treefrogapps.desktop.linux.sensor.monitor.ui.adapter

import javafx.scene.control.ListCell

class SensorListItemCell(private val controller: SensorListItemController) : ListCell<SensorListItem>() {

    override fun updateItem(item: SensorListItem, empty: Boolean) {
        super.updateItem(item, empty)
        controller.bind(item)
    }
}