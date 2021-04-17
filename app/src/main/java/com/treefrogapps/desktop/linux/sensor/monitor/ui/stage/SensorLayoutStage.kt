package com.treefrogapps.desktop.linux.sensor.monitor.ui.stage


import com.treefrogapps.javafx.LayoutInflater
import com.treefrogapps.javafx.LayoutStage
import com.treefrogapps.rxjava3.Rx3Schedulers
import java.util.*
import javax.inject.Inject

class SensorLayoutStage @Inject constructor(
    schedulers: Rx3Schedulers,
    inflater: LayoutInflater,
    bundle: ResourceBundle)
    : LayoutStage(inflater, schedulers, bundle) {

    init {
        minWidth = bundle.getString("app_min_width").toDouble()
        minHeight = bundle.getString("app_min_height").toDouble()
    }
}

