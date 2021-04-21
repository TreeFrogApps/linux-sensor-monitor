package com.treefrogapps.desktop.linux.sensor.monitor.data

import dagger.Binds
import dagger.Module
import java.util.function.Function

@Module abstract class DataModule {

    @Binds abstract fun sensorDataConverter(converter: SensorDataParser): Function<String, SensorData>

    @Binds abstract fun sensorDataFactory(factory: SensorDataFactory): CommandDataFactory<SensorData>

    @Binds abstract fun cpuDataConverter(converter: CpuFrequencyDataParser): Function<String, CpuFrequencyData>

    @Binds abstract fun cpuDataFactory(factory: CpuFrequencyDataFactory): CommandDataFactory<CpuFrequencyData>
}