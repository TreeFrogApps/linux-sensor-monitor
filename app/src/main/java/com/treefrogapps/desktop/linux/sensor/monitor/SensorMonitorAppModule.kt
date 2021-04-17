package com.treefrogapps.desktop.linux.sensor.monitor

import com.treefrogapps.desktop.linux.sensor.monitor.repository.SettingsModule
import com.treefrogapps.desktop.linux.sensor.monitor.resources.Resources
import com.treefrogapps.javafx.rxjava3.JavaFXSchedulers
import com.treefrogapps.rxjava3.Rx3Schedulers
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import java.util.concurrent.Executor
import javax.inject.Singleton

@Module(includes = [SettingsModule::class])
object SensorMonitorAppModule {

    @Provides
    @Singleton
    @JvmStatic
    fun schedulers(): Rx3Schedulers = object : Rx3Schedulers {
        override fun io(): Scheduler = Schedulers.io()
        override fun computation(): Scheduler = Schedulers.computation()
        override fun main(): Scheduler = JavaFXSchedulers.mainThread()
        override fun new(): Scheduler = Schedulers.newThread()
        override fun single(): Scheduler = Schedulers.single()
        override fun from(executor: Executor) = Schedulers.from(executor)
    }

    @Provides
    @Singleton
    @JvmStatic
    fun resources(): ResourceBundle = Resources

    @Provides
    @Singleton
    @JvmStatic
    fun s(): String = "The Value"
}