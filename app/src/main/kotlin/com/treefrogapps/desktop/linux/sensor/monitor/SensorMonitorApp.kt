package com.treefrogapps.desktop.linux.sensor.monitor


import com.treefrogapps.desktop.linux.sensor.monitor.data.SensorDataFactory
import com.treefrogapps.desktop.linux.sensor.monitor.ui.controllers.SensorLayoutController
import com.treefrogapps.desktop.linux.sensor.monitor.ui.stage.SensorLayoutStage
import com.treefrogapps.javafx.LayoutStageManager
import com.treefrogapps.javafx.dagger.ApplicationInjector
import com.treefrogapps.javafx.dagger.DaggerApplication
import com.treefrogapps.javafx.rxjava3.JavaFXSchedulers
import com.treefrogapps.rxjava3.Rx3Schedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import javafx.stage.Stage
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class SensorMonitorApp : DaggerApplication() {

    @Inject lateinit var schedulers: Rx3Schedulers
    @Inject lateinit var layoutStageManager: LayoutStageManager

    override fun start(primaryStage: Stage) {
        super.start(primaryStage)

        layoutStageManager.launch(
            SensorLayoutStage::class.java,
            SensorLayoutController::class.java,
            mapOf(Pair("A", "1")))

        Flowable.interval(2, TimeUnit.SECONDS)
            .flatMap { Flowable.fromCallable { SensorDataFactory().get() } }
            .subscribeOn(Schedulers.io())
            .subscribe { println(it) }
    }

    override fun stop() {
        super.stop()
        JavaFXSchedulers.shutDown()
    }

    override fun component(): ApplicationInjector<out DaggerApplication> =
        DaggerSensorMonitorAppComponent.factory().addApp(this)
}
