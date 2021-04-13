package com.treefrogapps.desktop.linux.sensor.monitor.data

import com.treefrogapps.desktop.linux.sensor.monitor.data.SensorData.DeviceType.UNKNOWN
import java.util.regex.Pattern


data class SensorData(val devices: List<Device> = listOf()) {

    enum class DeviceType(val type: String) {
        WIFI("iwlwifi"),
        CPU("coretemp"),
        NVME("nvme"),
        UNKNOWN("unknown")
    }

    data class DeviceTemperature(
        val sensor: String = "",
        val current: String = "",
        val high: String? = null,
        val critical: String? = null) {

        companion object {

            @JvmStatic private val TEMP_REGEX: Pattern by lazy { "[^0-9.0-9]+".toRegex().toPattern() }

            @JvmStatic private fun tempToDoubleOrZero(tempValue: String): Double =
                TEMP_REGEX.matcher(tempValue)
                    .results()
                    .findFirst()
                    .map { it.group() }
                    .map { it.toDoubleOrNull() }
                    .orElse(0.0)!!

            @JvmStatic fun DeviceTemperature.currentToDouble(): Double = tempToDoubleOrZero(current)
            @JvmStatic fun DeviceTemperature.highToDouble(): Double = tempToDoubleOrZero(current)
            @JvmStatic fun DeviceTemperature.criticalToDouble(): Double = tempToDoubleOrZero(current)
        }
    }

    data class Device(
        val type: DeviceType = UNKNOWN,
        val name: String = "",
        val adapterName: String = "",
        val temperatures: List<DeviceTemperature> = listOf()
    )
}