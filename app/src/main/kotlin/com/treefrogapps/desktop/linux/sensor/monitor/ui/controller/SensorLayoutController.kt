package com.treefrogapps.desktop.linux.sensor.monitor.ui.controller

import com.treefrogapps.desktop.linux.sensor.monitor.ui.adapter.SensorListItem
import com.treefrogapps.desktop.linux.sensor.monitor.ui.adapter.SensorListItem.Companion.toSensorListItems
import com.treefrogapps.desktop.linux.sensor.monitor.ui.model.SensorLayoutModel
import com.treefrogapps.desktop.linux.sensor.monitor.ui.model.SensorPollRateData
import com.treefrogapps.javafx.LayoutController
import com.treefrogapps.javafx.dagger.Scopes.ControllerScope
import com.treefrogapps.javafx.rxjava3.ObservableListFlowable.DisposableObservableList
import com.treefrogapps.javafx.rxjava3.ObservableListFlowable.toObservableList
import com.treefrogapps.javafx.rxjava3.ObservableValueFlowable.ChangeEvent
import com.treefrogapps.javafx.rxjava3.ObservableValueFlowable.toChangeFlowable
import com.treefrogapps.rxjava3.Rx3Schedulers
import com.treefrogapps.rxjava3.withSchedulers
import io.reactivex.rxjava3.core.Flowable
import javafx.beans.value.ChangeListener
import javafx.collections.ListChangeListener
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.stage.Stage
import javafx.util.Callback
import org.apache.logging.log4j.LogManager
import java.net.URL
import javax.inject.Inject

@ControllerScope class SensorLayoutController @Inject constructor(
    private val model: SensorLayoutModel,
    private val sensorCellFactory: Callback<ListView<SensorListItem>, ListCell<SensorListItem>>,
    private val schedulers: Rx3Schedulers) : LayoutController() {

    private val currentTab: Tab get() = sensorTabPane.selectionModel.selectedItem
    private val cpuLoadingListener: ListChangeListener<SensorListItem> = ListChangeListener { cpuLoadingIndicator.isVisible = it.list.isEmpty() }
    private val cpuPollRateListener: ChangeListener<Int> = ChangeListener { _, _, rate -> model.updateCpuPollRate(rate) }
    private val otherLoadingListener: ListChangeListener<SensorListItem> = ListChangeListener { otherLoadingIndicator.isVisible = it.list.isEmpty() }
    private val otherPollRateListener: ChangeListener<Int> = ChangeListener { _, _, rate -> model.updateOtherPollRate(rate) }
    @field:FXML private lateinit var sensorTabPane: TabPane

    // CPU Tab
    @field:FXML private lateinit var cpuTab: Tab
    @field:FXML private lateinit var cpuCriticalCheckbox: CheckBox
    @field:FXML private lateinit var cpuPollRateSpinner: Spinner<Int>
    @field:FXML private lateinit var cpuLoadingIndicator: ProgressIndicator
    @field:FXML private lateinit var cpuListView: ListView<SensorListItem>

    // Other Tab
    @field:FXML private lateinit var otherCriticalCheckbox: CheckBox
    @field:FXML private lateinit var otherPollRateSpinner: Spinner<Int>
    @field:FXML private lateinit var otherLoadingIndicator: ProgressIndicator
    @field:FXML private lateinit var otherListView: ListView<SensorListItem>

    override fun onInitialized(location: URL?) = LogManager.getLogger().info("onInitialized: $location")

    override fun onUpdate(args: Map<String, String>) {}

    override fun onStart(parent: Stage) {
        parent.title = resources?.getString("app_title")
        cpuListView.setupCpuList()
        cpuPollRateSpinner.setupSpinner(model.cpuPollData(), cpuPollRateListener)
        otherListView.setupOtherList()
        otherPollRateSpinner.setupSpinner(model.otherPollData(), otherPollRateListener)
    }

    override fun onStop() {
        cpuListView.teardownCpuList()
        cpuPollRateSpinner.teardownSpinner(cpuPollRateListener)
        otherListView.teardownOtherList()
        otherPollRateSpinner.teardownSpinner(otherPollRateListener)
    }

    private fun ChangeEvent<Tab>.isCpuTabActive(): Boolean = this.newValue == cpuTab

    private fun ChangeEvent<Tab>.isOtherTabActive(): Boolean = !isCpuTabActive()

    private fun ListView<SensorListItem>.setupCpuList() {
        cellFactory = sensorCellFactory
        items = cpuDataObservableList()
        items.addListener(cpuLoadingListener)
    }

    private fun ListView<SensorListItem>.teardownCpuList() {
        (items as? DisposableObservableList)?.dispose()
        items.removeListener(cpuLoadingListener)
        items = null
    }

    private fun cpuDataObservableList(): DisposableObservableList<SensorListItem> =
        sensorTabPane.selectionModel.selectedItemProperty()
            .toChangeFlowable()
            .startWithItem(ChangeEvent(currentTab, currentTab))
            .switchMap { if (it.isCpuTabActive()) model.observeCpuData() else Flowable.empty() }
            .map { it.toSensorListItems() }
            .withSchedulers(schedulers.io(), schedulers.main())
            .toObservableList()

    private fun ListView<SensorListItem>.setupOtherList() {
        cellFactory = sensorCellFactory
        items = otherDataObservableList()
        items.addListener(otherLoadingListener)
    }

    private fun ListView<SensorListItem>.teardownOtherList() {
        (items as? DisposableObservableList)?.dispose()
        items.removeListener(otherLoadingListener)
        items = null
    }

    private fun otherDataObservableList(): DisposableObservableList<SensorListItem> =
        sensorTabPane.selectionModel.selectedItemProperty()
            .toChangeFlowable()
            .startWithItem(ChangeEvent(currentTab, currentTab))
            .switchMap { if (it.isOtherTabActive()) model.observeSensorData() else Flowable.empty() }
            .map { data -> data.map { it.toSensorListItems().toMutableList() }.reduce { acc, items -> acc.apply { addAll(items) } } }
            .map(MutableList<SensorListItem>::toList)
            .withSchedulers(schedulers.io(), schedulers.main())
            .toObservableList()

    private fun Spinner<Int>.setupSpinner(pollData: SensorPollRateData, listener: ChangeListener<Int>) {
        pollData
            .run { SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, current) }
            .run { valueFactory = this }
            .run { valueProperty().addListener(listener) }
    }

    private fun Spinner<Int>.teardownSpinner(listener: ChangeListener<Int>) = valueProperty().removeListener(listener)
}