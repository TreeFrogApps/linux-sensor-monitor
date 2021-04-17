package com.treefrogapps.desktop.linux.sensor.monitor.repository

import dagger.Module
import dagger.Provides
import java.util.prefs.Preferences

@Module object SettingsModule {

    @Provides
    fun prefs(): Preferences = Preferences.userRoot().node(SettingsRepository::class.java.name)
}