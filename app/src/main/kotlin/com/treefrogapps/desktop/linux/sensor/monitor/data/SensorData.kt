package com.treefrogapps.desktop.linux.sensor.monitor.data

import com.treefrogapps.desktop.linux.sensor.monitor.data.SensorData.DeviceType.UNKNOWN
import java.util.*
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

            @JvmStatic private val TEMP_REGEX: Pattern by lazy { "[0-9.0-9]+".toRegex().toPattern() }

            @JvmStatic private fun tempToDoubleOrZero(tempValue: String?, def: Double = 0.0): Double =
                Optional.ofNullable(tempValue)
                    .flatMap {
                        TEMP_REGEX.matcher(it)
                            .results()
                            .findFirst()
                            .map { it.group() }
                            .map { it.toDoubleOrNull() }
                    }.orElse(def)!!

            @JvmStatic fun DeviceTemperature.currentToDouble(): Double = tempToDoubleOrZero(current, 100.0)
            @JvmStatic fun DeviceTemperature.highToDouble(): Double = tempToDoubleOrZero(high, 100.0)
            @JvmStatic fun DeviceTemperature.criticalToDouble(): Double = tempToDoubleOrZero(critical, 100.0)
            @JvmStatic fun DeviceTemperature.currentMaxProgress(): Double = currentToDouble() / criticalToDouble()
        }
    }

    data class Device(
        val type: DeviceType = UNKNOWN,
        val name: String = "",
        val adapterName: String = "",
        val temperatures: List<DeviceTemperature> = listOf()
    )
}