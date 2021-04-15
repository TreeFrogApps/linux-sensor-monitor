package com.treefrogapps.desktop.linux.sensor.monitor.ui.adapter

import javafx.scene.paint.Color

data class SensorListItem(
    val currentMaxTempPercent : Double = 0.0,
    val currentFormatted : String = "",
    val currentColor : TempColor,
    val sensorName : String = "",
    val deviceName : String? = null) {

    enum class TempColor(val value : String) {
        COOL("-bar-color: #16b3e9;"),
        NORMAL("-bar-color: #1de26a;"),
        WARM("-bar-color: #e2ce1d;"),
        HOT("-bar-color: #f83b07;")
    }
}