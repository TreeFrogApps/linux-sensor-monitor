package com.treefrogapps.desktop.linux.sensor.monitor.ui.controllers

import com.treefrogapps.javafx.LayoutController
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.fxml.FXML
import javafx.scene.control.TextField
import org.apache.logging.log4j.LogManager
import java.net.URL

class SensorLayoutController : LayoutController() {

    private val textBinding: StringProperty = SimpleStringProperty("")

    @field:FXML lateinit var textField2: TextField

    override fun onInitialized(location: URL?) {
        LogManager.getLogger().error("Mark : onCreate: $location")
        textField2.textProperty().bind(textBinding)
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