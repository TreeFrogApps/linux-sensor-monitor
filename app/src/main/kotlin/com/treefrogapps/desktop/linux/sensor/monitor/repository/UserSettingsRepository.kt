package com.treefrogapps.desktop.linux.sensor.monitor.repository

import com.treefrogapps.javafx.dagger.Scopes.InitializableScope
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

@InitializableScope class UserSettingsRepository @Inject constructor() {

    inline fun <reified T> observe(key: String): Flowable<T> = Flowable.empty()
}