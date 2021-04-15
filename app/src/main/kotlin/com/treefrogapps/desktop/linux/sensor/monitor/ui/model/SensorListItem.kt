package com.treefrogapps.desktop.linux.sensor.monitor.ui.model

import javafx.scene.paint.Color

data class SensorListItem(
    val deviceName : String? = null,
    val sensorName : String = "",
    val min : Double = 0.0,
    val current : Double = 0.0,
    val currentFormatted : String = "",
    val currentColor : Color,
    val max : Double = 100.0)