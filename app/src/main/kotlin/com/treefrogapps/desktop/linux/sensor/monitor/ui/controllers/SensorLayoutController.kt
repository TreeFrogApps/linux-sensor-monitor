package com.treefrogapps.desktop.linux.sensor.monitor.ui.controllers

import com.treefrogapps.desktop.linux.sensor.monitor.repository.UserSettingsRepository
import com.treefrogapps.desktop.linux.sensor.monitor.ui.adapter.SensorListItem
import com.treefrogapps.javafx.LayoutController
import com.treefrogapps.javafx.dagger.Scopes.ControllerScope
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.fxml.FXML
import javafx.scene.control.CheckBox
import javafx.scene.control.ListView
import javafx.scene.control.TextField
import org.apache.logging.log4j.LogManager
import java.net.URL
import javax.inject.Inject

@ControllerScope class SensorLayoutController @Inject constructor(
    val userSettingsRepository: UserSettingsRepository)
    : LayoutController() {

    private val textBinding: StringProperty = SimpleStringProperty("")

    @field:FXML lateinit var cpuCriticalAlert: CheckBox
    @field:FXML lateinit var textField1: TextField
     @field:FXML lateinit var cpuListView: ListView<SensorListItem>

    override fun onInitialized(location: URL?) {
        LogManager.getLogger().error("Mark : onCreate: $location")
         textField1.textProperty().bind(textBinding)
    }

    override fun onUpdate(args: Map<String, String>) {
        LogManager.getLogger().error("Mark : onUpdate : $args")
        textBinding.value = args.toString()
    }

    override fun onStart() {
        LogManager.getLogger().error("Mark : onStart : $args")
        textBinding.value = args.toString()
    }

    override fun onStop() {
        LogManager.getLogger().error("Mark : onStop")
    }
}