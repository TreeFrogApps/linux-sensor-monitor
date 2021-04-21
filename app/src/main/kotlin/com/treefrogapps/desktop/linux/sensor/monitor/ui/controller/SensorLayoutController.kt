package com.treefrogapps.desktop.linux.sensor.monitor.ui.controller

import com.treefrogapps.desktop.linux.sensor.monitor.data.SensorData.Device
import com.treefrogapps.desktop.linux.sensor.monitor.data.SensorData.DeviceTemperature.Companion.criticalToDouble
import com.treefrogapps.desktop.linux.sensor.monitor.data.SensorData.DeviceTemperature.Companion.currentToDouble
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
import com.treefrogapps.javafx.rxjava3.ObservableValueFlowable.toObservableString
import com.treefrogapps.javafx.rxjava3.ObservableValueFlowable.toValueFlowable
import com.treefrogapps.rxjava3.Rx3Schedulers
import com.treefrogapps.rxjava3.plusAssign
import com.treefrogapps.rxjava3.rxSubscriber
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javafx.beans.value.ChangeListener
import javafx.collections.ListChangeListener
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.stage.Stage
import javafx.util.Callback
import org.apache.logging.log4j.LogManager
import java.net.URL
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Inject

@ControllerScope class SensorLayoutController @Inject constructor(
    private val model: SensorLayoutModel,
    private val sensorCellFactory: Callback<ListView<SensorListItem>, ListCell<SensorListItem>>,
    private val sensorResources: SensorLayoutResources,
    private val schedulers: Rx3Schedulers) : LayoutController() {

    companion object {
        private const val APP_TITLE = "app_title"
        private const val ALERT_THROTTLE_SECONDS = 10L
    }

    private val currentTab: Tab get() = sensorTabPane.selectionModel.selectedItem
    private val cpuLoadingListener: ListChangeListener<SensorListItem> = ListChangeListener { cpuLoadingIndicator.isVisible = it.list.isEmpty() }
    private val cpuPollRateListener: ChangeListener<Int> = ChangeListener { _, _, rate -> model.updateCpuPollRate(rate) }
    private val otherLoadingListener: ListChangeListener<SensorListItem> = ListChangeListener { otherLoadingIndicator.isVisible = it.list.isEmpty() }
    private val otherPollRateListener: ChangeListener<Int> = ChangeListener { _, _, rate -> model.updateOtherPollRate(rate) }
    private val disposable: CompositeDisposable = CompositeDisposable()

    @field:FXML private lateinit var sensorTabPane: TabPane

    // CPU Tab
    @field:FXML private lateinit var cpuTab: Tab
    @field:FXML private lateinit var cpuCriticalCheckbox: CheckBox
    @field:FXML private lateinit var cpuPollRateSpinner: Spinner<Int>
    @field:FXML private lateinit var cpuLoadingIndicator: ProgressIndicator
    @field:FXML private lateinit var cpuListView: ListView<SensorListItem>
    @field:FXML private lateinit var cpuName: Label
    @field:FXML private lateinit var cpuFrequencies: Label

    // Other Tab
    @field:FXML private lateinit var otherCriticalCheckbox: CheckBox
    @field:FXML private lateinit var otherPollRateSpinner: Spinner<Int>
    @field:FXML private lateinit var otherLoadingIndicator: ProgressIndicator
    @field:FXML private lateinit var otherListView: ListView<SensorListItem>

    override fun onInitialized(location: URL?) = LogManager.getLogger().info("onInitialized: $location")

    override fun onUpdate(args: Map<String, String>) {}

    override fun onStart(parent: Stage) {
        parent.title = resources?.getString(APP_TITLE)
        cpuListView.setupCpuList()
        otherListView.setupOtherList()
        cpuPollRateSpinner.setupSpinner(model.cpuPollData(), cpuPollRateListener)
        otherPollRateSpinner.setupSpinner(model.otherPollData(), otherPollRateListener)
        sensorTabPane.setupCpuFrequencyInfoObservable()
        cpuCriticalCheckbox.startCpuCriticalTempMonitor()
        otherCriticalCheckbox.startOtherCriticalTempMonitor()
    }

    override fun onStop() {
        disposable.clear()
        cpuListView.items?.removeListener(cpuLoadingListener)
        otherListView.items?.removeListener(otherLoadingListener)
        cpuPollRateSpinner.removeListener(cpuPollRateListener)
        otherPollRateSpinner.removeListener(otherPollRateListener)
    }

    private fun ChangeEvent<Tab>.isCpuTabActive(): Boolean = this.newValue == cpuTab

    private fun ChangeEvent<Tab>.isOtherTabActive(): Boolean = !isCpuTabActive()

    private fun ListView<SensorListItem>.setupCpuList() {
        cellFactory = sensorCellFactory
        disposable += cpuDataObservableList()
        items.addListener(cpuLoadingListener)
    }

    private fun ListView<SensorListItem>.cpuDataObservableList(): DisposableObservableList<SensorListItem> =
        sensorTabPane.selectionModel.selectedItemProperty()
            .toChangeFlowable()
            .observeOn(schedulers.io())
            .startWithItem(ChangeEvent(currentTab, currentTab))
            .switchMap { if (it.isCpuTabActive()) model.observeCpuData() else Flowable.empty() }
            .map { it.toSensorListItems() }
            .observeOn(schedulers.main())
            .toObservableList(this)

    private fun TabPane.setupCpuFrequencyInfoObservable() {
        val frequencyData = selectionModel.selectedItemProperty()
            .toChangeFlowable()
            .observeOn(schedulers.io())
            .startWithItem(ChangeEvent(currentTab, currentTab))
            .switchMap { if (it.isCpuTabActive()) model.observeCpuFrequencyData() else Flowable.empty() }
            .publish(1)
            .refCount()

        frequencyData
            .map { fd -> fd.name }
            .distinctUntilChanged()
            .observeOn(schedulers.main())
            .toObservableString(cpuName.textProperty())
            .let(disposable::add)

        frequencyData
            .map(sensorResources::formattedFrequencies)
            .distinctUntilChanged()
            .observeOn(schedulers.main())
            .toObservableString(cpuFrequencies.textProperty())
            .let(disposable::add)
    }

    private fun ListView<SensorListItem>.setupOtherList() {
        cellFactory = sensorCellFactory
        disposable += otherDataObservableList()
        items.addListener(otherLoadingListener)
    }

    private fun ListView<SensorListItem>.otherDataObservableList(): DisposableObservableList<SensorListItem> =
        sensorTabPane.selectionModel.selectedItemProperty()
            .toChangeFlowable()
            .observeOn(schedulers.io())
            .startWithItem(ChangeEvent(currentTab, currentTab))
            .switchMap { if (it.isOtherTabActive()) model.observeSensorData() else Flowable.empty() }
            .map { data -> data.map { it.toSensorListItems().toMutableList() }.reduce { acc, items -> acc.apply { addAll(items) } } }
            .map(MutableList<SensorListItem>::toList)
            .observeOn(schedulers.main())
            .toObservableList(this)

    private fun Spinner<Int>.setupSpinner(pollData: SensorPollRateData, listener: ChangeListener<Int>) {
        pollData
            .run { SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, current) }
            .run { valueFactory = this }
            .run { valueProperty().addListener(listener) }
    }

    private fun Spinner<Int>.removeListener(listener: ChangeListener<Int>) = valueProperty().removeListener(listener)

    private fun CheckBox.startCpuCriticalTempMonitor() =
        model.observeCpuData()
            .map { it.hasCriticalTemp() }
            .run { criticalAlertListener(this) }

    private fun CheckBox.startOtherCriticalTempMonitor() =
        model.observeSensorData()
            .map { it.hasCriticalTemp() }
            .run { criticalAlertListener(this) }

    private fun CheckBox.criticalAlertListener(criticalMonitorObservable: Flowable<Boolean>) {
        disposable += selectedProperty()
            .toValueFlowable()
            .observeOn(schedulers.io())
            .startWithItem(cpuCriticalCheckbox.isSelected)
            .switchMap { isChecked -> if (isChecked) criticalMonitorObservable else Flowable.empty() }
            .throttleFirst(ALERT_THROTTLE_SECONDS, SECONDS)
            .observeOn(schedulers.main())
            .rxSubscriber({ isCritical -> if (isCritical) model.playAlert() })
    }

    private fun List<Device>.hasCriticalTemp(): Boolean = any { it.hasCriticalTemp() }

    private fun Device.hasCriticalTemp(): Boolean = temperatures.any { it.currentToDouble() >= it.criticalToDouble() }
}