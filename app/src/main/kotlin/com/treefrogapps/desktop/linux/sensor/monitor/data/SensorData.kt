package com.treefrogapps.desktop.linux.sensor.monitor.data

import com.treefrogapps.desktop.linux.sensor.monitor.data.SensorData.DeviceType.UNKNOWN
import com.treefrogapps.kotlin.core.extensions.orElse
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

            @JvmStatic private fun tempToDoubleOrNull(tempValue: String?): Double? =
                Optional.ofNullable(tempValue)
                    .flatMap {
                        TEMP_REGEX.matcher(it)
                            .results()
                            .findFirst()
                            .map { r -> r.group() }
                            .map { s -> s.toDoubleOrNull() }
                    }.orElse(null)

            @JvmStatic fun DeviceTemperature.currentToDouble(): Double = tempToDoubleOrNull(current).orElse { 100.0 }
            @JvmStatic fun DeviceTemperature.highToDouble(): Double = tempToDoubleOrNull(high).orElse { 100.0 }
            @JvmStatic fun DeviceTemperature.criticalToDouble(): Double = tempToDoubleOrNull(critical).orElse { highToDouble() }
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