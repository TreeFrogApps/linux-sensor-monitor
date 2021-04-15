package com.treefrogapps.desktop.linux.sensor.monitor.ui.stage


import com.treefrogapps.javafx.LayoutInflater
import com.treefrogapps.javafx.LayoutStage
import com.treefrogapps.rxjava3.Rx3Schedulers
import javafx.scene.Scene
import java.util.*
import javax.inject.Inject

class SensorLayoutStage @Inject constructor(
    schedulers: Rx3Schedulers,
    inflater: LayoutInflater,
    bundle: ResourceBundle)
    : LayoutStage(inflater, schedulers, bundle) {

    init {
        minWidth = 400.0;
        minHeight = 230.0;
    }
}

