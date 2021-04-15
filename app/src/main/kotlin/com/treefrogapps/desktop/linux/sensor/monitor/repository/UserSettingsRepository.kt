package com.treefrogapps.desktop.linux.sensor.monitor.repository

import com.treefrogapps.rxjava3.Rx3Watchers
import io.reactivex.rxjava3.core.Flowable
import java.util.prefs.PreferenceChangeEvent
import java.util.prefs.Preferences
import javax.inject.Inject

class UserSettingsRepository @Inject constructor(private val preferences: Preferences) {

    companion object {
        const val UPDATE_INTERVAL_KEY = "update_interval"
    }

    fun setSetting(key: String, value : String) = preferences.put(key, value)

    fun getSetting(key: String, default : String) = preferences.get(key, default)

    fun observeSetting(key: String): Flowable<String> =
        Rx3Watchers.rx3PreferencesWatcher(prefs = preferences, filter = { it == key })
            .map(PreferenceChangeEvent::getNewValue)
            .startWithItem(preferences.get(key, ""))
}