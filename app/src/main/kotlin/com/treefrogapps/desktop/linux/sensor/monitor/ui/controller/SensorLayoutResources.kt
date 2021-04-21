package com.treefrogapps.desktop.linux.sensor.monitor.ui.controller

import com.treefrogapps.desktop.linux.sensor.monitor.data.CpuFrequencyData
import com.treefrogapps.javafx.dagger.Scopes.ControllerScope
import java.text.DecimalFormat
import java.util.*
import javax.inject.Inject

@ControllerScope class SensorLayoutResources @Inject constructor(private val resources: ResourceBundle) {

    companion object {
        private const val CPU_FREQUENCIES_PLACEHOLDER = "cpu_frequencies_placeholder"

        @JvmStatic private val formatter: DecimalFormat = DecimalFormat("#.##")
    }

    fun formattedFrequencies(frequencyData: CpuFrequencyData): String =
        with(frequencyData) {
            String.format(
                resources.getString(CPU_FREQUENCIES_PLACEHOLDER),
                formatter.format(currentGhz),
                formatter.format(minGhz),
                formatter.format(maxGhz))
        }
}