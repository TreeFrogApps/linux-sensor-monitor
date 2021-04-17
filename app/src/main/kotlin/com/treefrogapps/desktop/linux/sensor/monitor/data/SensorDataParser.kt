package com.treefrogapps.desktop.linux.sensor.monitor.data

import com.treefrogapps.desktop.linux.sensor.monitor.data.SensorData.Device
import com.treefrogapps.desktop.linux.sensor.monitor.data.SensorData.DeviceTemperature
import com.treefrogapps.desktop.linux.sensor.monitor.data.SensorData.DeviceType.*
import java.util.function.Function
import java.util.regex.MatchResult
import java.util.regex.Pattern
import java.util.stream.Collectors

class SensorDataParser : Function<String, SensorData> {

    companion object {
        @JvmStatic
        private val TEMP_PATTERN: Pattern by lazy { "(?:[+]\\d{1,3}\\.\\d{1,2}[°][C])".toRegex().toPattern() }
    }

    override fun apply(p0: String): SensorData =
        p0.split("\n")
            .iterator().run {
                mutableListOf<Device>().apply {
                    while (hasNext()) {
                        val nextLine = next()
                        when (getNextDevice(nextLine)) {
                            WIFI    -> createDevice(WIFI, nextLine)
                            CPU     -> createDevice(CPU, nextLine)
                            NVME    -> createDevice(NVME, nextLine)
                            UNKNOWN -> createDevice(UNKNOWN, nextLine)
                            null    -> null
                        }?.let(::add)
                    }
                }.let(::SensorData)
            }

    private fun getNextDevice(line: String): SensorData.DeviceType? =
        when {
            line.isEmpty() || line.isBlank() -> null
            line.startsWith(WIFI.type)       -> WIFI
            line.startsWith(CPU.type)        -> CPU
            line.startsWith(NVME.type)       -> NVME
            else                             -> UNKNOWN
        }

    private fun Iterator<String>.createDevice(type: SensorData.DeviceType, name : String): Device {
        var adapterType = ""
        val temperatures = mutableListOf<DeviceTemperature>()
        while (hasNext()) {
            val line = next().split(":")
            when (line.size) {
                2    -> {
                    if (line[0].startsWith("Adapter")) {
                        adapterType = line[1].trim()
                    } else line[1].trim().toTemperature(line[0])?.let(temperatures::add)
                }
                else -> break
            }
        }
        return Device(
            type = type,
            name = name,
            adapterName = adapterType,
            temperatures = temperatures
        )
    }

    private fun String.toTemperature(sensor : String): DeviceTemperature? =
        let(TEMP_PATTERN::matcher).results()
            ?.map(MatchResult::group)
            ?.collect(Collectors.toList())
            ?.filter(String::isNotEmpty)
            ?.takeIf(List<String>::isNotEmpty)
            ?.run {
                DeviceTemperature(
                    sensor = sensor,
                    current = getOrElse(0) { "" },
                    high = getOrNull(1),
                    critical = getOrNull(2)
                )
            }
}

/*

Example "sensors" data dump :

ucsi_source_psy_USBC000:002-isa-0000
Adapter: ISA adapter
in0:           0.00 V  (min =  +0.00 V, max =  +0.00 V)
curr1:         0.00 A  (max =  +0.00 A)

iwlwifi_1-virtual-0
Adapter: Virtual device
temp1:        +37.0°C

coretemp-isa-0000
Adapter: ISA adapter
Package id 0:  +41.0°C  (high = +100.0°C, crit = +100.0°C)
Core 0:        +40.0°C  (high = +100.0°C, crit = +100.0°C)
Core 1:        +40.0°C  (high = +100.0°C, crit = +100.0°C)
Core 2:        +41.0°C  (high = +100.0°C, crit = +100.0°C)
Core 3:        +39.0°C  (high = +100.0°C, crit = +100.0°C)
Core 4:        +40.0°C  (high = +100.0°C, crit = +100.0°C)
Core 5:        +40.0°C  (high = +100.0°C, crit = +100.0°C)
Core 6:        +40.0°C  (high = +100.0°C, crit = +100.0°C)
Core 7:        +40.0°C  (high = +100.0°C, crit = +100.0°C)

BAT0-acpi-0
Adapter: ACPI interface
in0:          12.81 V
curr1:         0.00 A

ucsi_source_psy_USBC000:001-isa-0000
Adapter: ISA adapter
in0:           0.00 V  (min =  +0.00 V, max =  +0.00 V)
curr1:         0.00 A  (max =  +0.00 A)

pch_cometlake-virtual-0
Adapter: Virtual device
temp1:        +49.0°C

nvme-pci-3b00
Adapter: PCI adapter
Composite:    +28.9°C  (low  =  -0.1°C, high = +76.8°C)
                       (crit = +79.8°C)

acpitz-acpi-0
Adapter: ACPI interface
temp1:        +42.0°C  (crit = +100.0°C)
temp2:        +42.0°C  (crit = +100.0°C)

 */