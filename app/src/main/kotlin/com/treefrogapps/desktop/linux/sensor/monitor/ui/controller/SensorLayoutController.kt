package com.treefrogapps.desktop.linux.sensor.monitor.ui.controller

import com.treefrogapps.desktop.linux.sensor.monitor.ui.adapter.SensorListItem
import com.treefrogapps.desktop.linux.sensor.monitor.ui.adapter.SensorListItem.Companion.toSensorListItems
import com.treefrogapps.desktop.linux.sensor.monitor.ui.model.CpuSensorLayoutModel
import com.treefrogapps.desktop.linux.sensor.monitor.ui.model.OtherSensorLayoutModel
import com.treefrogapps.javafx.LayoutController
import com.treefrogapps.javafx.dagger.Scopes.ControllerScope
import com.treefrogapps.javafx.rxjava3.ObservableListFlowable.DisposableObservableList
import com.treefrogapps.javafx.rxjava3.ObservableListFlowable.toObservableList
import com.treefrogapps.javafx.rxjava3.ObservableValueFlowable
import com.treefrogapps.javafx.rxjava3.ObservableValueFlowable.ChangeEvent
import com.treefrogapps.javafx.rxjava3.ObservableValueFlowable.toChangeFlowable
import com.treefrogapps.rxjava3.Rx3Schedulers
import com.treefrogapps.rxjava3.withSchedulers
import io.reactivex.rxjava3.core.Flowable
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.util.Callback
import org.apache.logging.log4j.LogManager
import java.net.URL
import javax.inject.Inject

@ControllerScope class SensorLayoutController @Inject constructor(
    private val cpuModel: CpuSensorLayoutModel,
    private val otherModel: OtherSensorLayoutModel,
    private val cellFactory: Callback<ListView<SensorListItem>, ListCell<SensorListItem>>,
    private val schedulers: Rx3Schedulers)
    : LayoutController() {

    @field:FXML private lateinit var sensorTabPane: TabPane

    // CPU Tab
    @field:FXML private lateinit var cpuTab: Tab
    @field:FXML private lateinit var cpuCriticalCheckbox: CheckBox
    @field:FXML private lateinit var cpuListView: ListView<SensorListItem>
    private lateinit var cpuDataObservableList: DisposableObservableList<SensorListItem>

    // Other Tab
    @field:FXML private lateinit var otherTab: Tab

    override fun onInitialized(location: URL?) {
        LogManager.getLogger().info("Mark : onCreate: $location")
    }

    override fun onUpdate(args: Map<String, String>) {
    }

    override fun onStart() {
        cpuDataObservableList = cpuDataObservableList()
        cpuListView.cellFactory = cellFactory
        cpuListView.items = cpuDataObservableList

    }

    override fun onStop() {
        LogManager.getLogger().error("Mark : onStop")
        cpuDataObservableList.dispose()
        cpuListView.items = null
    }

    private fun ChangeEvent<Tab>.isCpuTabActive(): Boolean = this.newValue == cpuTab

    private fun ChangeEvent<Tab>.isOtherTabActive(): Boolean = !isCpuTabActive()

    private fun cpuDataObservableList(): DisposableObservableList<SensorListItem> =
        sensorTabPane.selectionModel.selectedItemProperty()
            .toChangeFlowable()
            .startWithItem(ChangeEvent(sensorTabPane.selectionModel.selectedItem, sensorTabPane.selectionModel.selectedItem))
            .switchMap { if (it.isCpuTabActive()) cpuModel.observeCpuData() else Flowable.empty() }
            .map { it.toSensorListItems() }
            .withSchedulers(schedulers.io(), schedulers.main())
            .toObservableList()
}