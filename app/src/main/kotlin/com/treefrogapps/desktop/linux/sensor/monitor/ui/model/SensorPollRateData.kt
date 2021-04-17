package com.treefrogapps.desktop.linux.sensor.monitor.ui.model

data class SensorPollRateData(
    val min: Int = 1,
    val max: Int = 10,
    val current: Int)