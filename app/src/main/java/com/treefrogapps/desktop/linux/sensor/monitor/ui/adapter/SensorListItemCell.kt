package com.treefrogapps.desktop.linux.sensor.monitor.ui.adapter

import com.treefrogapps.kotlin.core.extensions.ifNull
import javafx.scene.control.ListCell

class SensorListItemCell(private val controller: SensorListItemController) : ListCell<SensorListItem>() {

    override fun updateItem(item: SensorListItem?, empty: Boolean) {
        super.updateItem(item, empty)
        item?.run { controller.bind(this@SensorListItemCell, this) }
            .ifNull { controller.unbind(this@SensorListItemCell) }
    }
}