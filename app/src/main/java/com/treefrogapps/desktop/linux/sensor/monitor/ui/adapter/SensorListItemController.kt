package com.treefrogapps.desktop.linux.sensor.monitor.ui.adapter

import com.treefrogapps.javafx.dagger.Scopes.ControllerScope
import javafx.beans.property.DoubleProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Label
import javafx.scene.control.Labeled
import javafx.scene.control.ProgressBar
import javafx.scene.layout.HBox
import javafx.util.Callback
import java.net.URL
import java.util.*
import javax.inject.Inject

class SensorListItemController : Initializable {

    @ControllerScope class Factory @Inject constructor() : Callback<Class<SensorListItemController>, SensorListItemController> {
        override fun call(param: Class<SensorListItemController>?): SensorListItemController = SensorListItemController()
    }

    @field:FXML private lateinit var sensorItemRoot: HBox
    @field:FXML private lateinit var sensorTempPercentBar: ProgressBar
    @field:FXML private lateinit var currentTempLabel: Label
    @field:FXML private lateinit var sensorNameLabel: Label
    @field:FXML private lateinit var deviceNameLabel: Label

    private val percentDoubleProperty: DoubleProperty = SimpleDoubleProperty(0.0)
    private val percentStyleProperty: StringProperty = SimpleStringProperty("")
    private val currentTempTextProperty: StringProperty = SimpleStringProperty("")
    private val sensorNameTextProperty: StringProperty = SimpleStringProperty("")
    private val deviceNameTextProperty: StringProperty = SimpleStringProperty("")

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        sensorTempPercentBar.progressProperty().bind(percentDoubleProperty)
        sensorTempPercentBar.styleProperty().bind(percentStyleProperty)
        currentTempLabel.textProperty().bind(currentTempTextProperty)
        sensorNameLabel.textProperty().bind(sensorNameTextProperty)
        deviceNameLabel.textProperty().bind(deviceNameTextProperty)
    }

    fun bind(labeled: Labeled, item: SensorListItem) {
        with(item) {
            percentDoubleProperty.value = currentMaxTempProgress
            percentStyleProperty.value = currentColor.value
            currentTempTextProperty.value = currentFormatted
            sensorNameTextProperty.value = sensorName
            deviceNameTextProperty.value = deviceName
            labeled.graphic = sensorItemRoot
        }
    }

    fun unbind(labeled: Labeled) {
        percentDoubleProperty.value = 0.0
        percentStyleProperty.value = ""
        currentTempTextProperty.value = ""
        sensorNameTextProperty.value = ""
        deviceNameTextProperty.value = ""
        labeled.graphic = null
    }
}