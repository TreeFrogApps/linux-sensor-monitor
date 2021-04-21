package com.treefrogapps.desktop.linux.sensor.monitor


import com.treefrogapps.desktop.linux.sensor.monitor.data.CommandDataFactory
import com.treefrogapps.desktop.linux.sensor.monitor.data.CpuFrequencyData
import com.treefrogapps.desktop.linux.sensor.monitor.ui.controller.SensorLayoutController
import com.treefrogapps.desktop.linux.sensor.monitor.ui.stage.SensorLayoutStage
import com.treefrogapps.javafx.LayoutStageManager
import com.treefrogapps.javafx.dagger.ApplicationInjector
import com.treefrogapps.javafx.dagger.DaggerApplication
import com.treefrogapps.javafx.rxjava3.JavaFXSchedulers
import javafx.stage.Stage
import javax.inject.Inject


class SensorMonitorApp : DaggerApplication() {

    @Inject lateinit var cpuFrequencyData: CommandDataFactory<CpuFrequencyData>
    @Inject lateinit var layoutStageManager: LayoutStageManager

    override fun start(primaryStage: Stage) {
        super.start(primaryStage)

        layoutStageManager.launch(SensorLayoutStage::class.java, SensorLayoutController::class.java)
    }

    override fun stop() {
        super.stop()
        JavaFXSchedulers.shutDown()
    }

    override fun component(): ApplicationInjector<out DaggerApplication> =
        DaggerSensorMonitorAppComponent.factory().addApp(this)
}
