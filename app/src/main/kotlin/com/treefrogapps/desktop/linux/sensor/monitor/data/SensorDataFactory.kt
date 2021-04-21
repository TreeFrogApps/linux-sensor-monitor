package com.treefrogapps.desktop.linux.sensor.monitor.data

import java.util.function.Function
import javax.inject.Inject

class SensorDataFactory @Inject constructor(private val dataConverter: Function<String, SensorData>) : CommandDataFactory<SensorData>() {

    override fun command(): String = "sensors"

    override fun adapt(output: String): SensorData = dataConverter.apply(output)
}