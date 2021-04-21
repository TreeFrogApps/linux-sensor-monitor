package com.treefrogapps.desktop.linux.sensor.monitor.data

import java.util.function.Function
import javax.inject.Inject

class CpuFrequencyDataFactory @Inject constructor(private val dataConverter: Function<String, CpuFrequencyData>)
    : CommandDataFactory<CpuFrequencyData>() {

    override fun command(): String = "lscpu"

    override fun adapt(output: String): CpuFrequencyData = dataConverter.apply(output)
}