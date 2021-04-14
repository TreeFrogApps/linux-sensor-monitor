package com.treefrogapps.desktop.linux.sensor.monitor.repository

import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class UserSettingsRepository @Inject constructor() {

    inline fun <reified T> observe(key: String): Flowable<T> = Flowable.empty()
}