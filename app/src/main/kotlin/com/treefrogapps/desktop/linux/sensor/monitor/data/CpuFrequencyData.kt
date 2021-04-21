package com.treefrogapps.desktop.linux.sensor.monitor.data


data class CpuFrequencyData(
    val name: String = "",
    val currentGhz: Double = 0.0,
    val maxGhz: Double = 0.0,
    val minGhz: Double = 0.0)