package com.treefrogapps.desktop.linux.sensor.monitor.ui.adapter

import com.treefrogapps.javafx.dagger.Scopes.ControllerScope
import javafx.beans.property.DoubleProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Label
import javafx.scene.control.ProgressBar
import javafx.util.Callback
import java.net.URL
import java.util.*
import javax.inject.Inject

class SensorListItemController : Initializable {

    @ControllerScope class Factory @Inject constructor() : Callback<Class<SensorListItemController>, SensorListItemController> {
        override fun call(param: Class<SensorListItemController>?): SensorListItemController = SensorListItemController()
    }

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

    fun bind(item: SensorListItem) {
        with(item) {
            percentDoubleProperty.value = currentMaxTempPercent
            percentStyleProperty.value = currentColor.value
            currentTempTextProperty.value = currentFormatted
            sensorNameTextProperty.value = sensorName
            deviceNameTextProperty.value = deviceName
        }
    }
}