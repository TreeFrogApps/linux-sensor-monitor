package com.treefrogapps.desktop.linux.sensor.monitor.ui.adapter

import com.treefrogapps.desktop.linux.sensor.monitor.data.SensorData.Device
import com.treefrogapps.desktop.linux.sensor.monitor.data.SensorData.DeviceTemperature
import com.treefrogapps.desktop.linux.sensor.monitor.data.SensorData.DeviceTemperature.Companion.currentMaxProgress

data class SensorListItem(
    val currentMaxTempProgress: Double = 0.0,
    val currentFormatted: String = "",
    val currentColor: TempColor,
    val sensorName: String = "",
    val deviceName: String? = null) {

    enum class TempColor(val value: String) {
        COOL("-bar-color: #16b3e9;"),
        NORMAL("-bar-color: #1de26a;"),
        WARM("-bar-color: #e2ce1d;"),
        HOT("-bar-color: #ea5F14;"),
        CRITICAL("-bar-color: #f83b07;")
    }

    companion object {

        @JvmStatic fun Device.toSensorListItems(): List<SensorListItem> = temperatures.map { it.toSensorListItem(this.name) }

        @JvmStatic private fun DeviceTemperature.toSensorListItem(deviceName: String?) =
            SensorListItem(
                currentMaxTempProgress = currentMaxProgress(),
                currentFormatted = "$current / ${critical ?: high ?: "?"}",
                currentColor = currentPercentToTempColor(currentMaxProgress()),
                sensorName = sensor,
                deviceName = deviceName
            )

        private fun currentPercentToTempColor(currentPercent: Double): TempColor =
            when (currentPercent) {
                in 0.0..0.3   -> TempColor.COOL
                in 0.3..0.7   -> TempColor.NORMAL
                in 0.7..0.85  -> TempColor.WARM
                in 0.85..0.95 -> TempColor.HOT
                else          -> TempColor.CRITICAL
            }
    }
}