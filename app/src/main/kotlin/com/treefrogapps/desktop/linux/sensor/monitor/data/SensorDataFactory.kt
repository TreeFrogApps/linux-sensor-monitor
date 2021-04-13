package com.treefrogapps.desktop.linux.sensor.monitor.data

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.function.Supplier

class SensorDataFactory : Supplier<SensorData> {

    companion object {
        @JvmStatic
        private val LINE_SEPARATOR: String by lazy { System.getProperty("line.separator") }

        @JvmStatic
        private fun String.toSensorData(): SensorData = SensorDataParser().apply(this)
    }

    override fun get(): SensorData =
        Runtime.getRuntime().exec("sensors").run {
            BufferedReader(InputStreamReader(inputStream))
                .lineSequence()
                .joinToString(separator = LINE_SEPARATOR)
        }.toSensorData()
}