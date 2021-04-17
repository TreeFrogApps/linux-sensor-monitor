package com.treefrogapps.desktop.linux.sensor.monitor.repository

import com.treefrogapps.desktop.linux.sensor.monitor.resources.Resources.getInteger
import com.treefrogapps.rxjava3.Rx3Watchers
import io.reactivex.rxjava3.core.Flowable
import java.util.*
import java.util.prefs.PreferenceChangeEvent
import java.util.prefs.Preferences
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    private val resources : ResourceBundle,
    private val preferences: Preferences) {

    companion object {
        const val CPU_UPDATE_INTERVAL_KEY = "cpu_sensor_update_interval_key"
        const val OTHER_UPDATE_INTERVAL_KEY = "other_sensor_update_interval_key"
    }

    fun setSetting(key: String, value : Int = 0) = preferences.putInt(key, value)

    fun getSetting(key: String, default : Int = 0) = preferences.getInt(key, default)

    fun observeIntSetting(key: String): Flowable<Int> =
        Rx3Watchers.rx3PreferencesWatcher(prefs = preferences, filter = { it == key })
            .map(PreferenceChangeEvent::getNewValue)
            .map(String::toInt)
            .startWithItem(preferences.getInt(key, resources.getInteger(key)))
}