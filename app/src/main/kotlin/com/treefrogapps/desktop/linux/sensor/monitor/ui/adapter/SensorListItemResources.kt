package com.treefrogapps.desktop.linux.sensor.monitor.ui.adapter

import com.treefrogapps.desktop.linux.sensor.monitor.ui.adapter.SensorListItem.TempColor
import com.treefrogapps.javafx.dagger.Scopes.ControllerScope
import java.util.*
import javax.inject.Inject

@ControllerScope class SensorListItemResources @Inject constructor(private val resources: ResourceBundle) {

    companion object {
        private const val BAR_STYLE_PLACEHOLDER = "bar_color_css_placeholder"
        private const val TEXT_FILL_STYLE_PLACEHOLDER = "text_fill_color_css_placeholder"
    }

    fun barStyle(tempColor: TempColor): String = String.format(resources.getString(BAR_STYLE_PLACEHOLDER), tempColor.value)
    fun textStyle(tempColor: TempColor): String = String.format(resources.getString(TEXT_FILL_STYLE_PLACEHOLDER), tempColor.value)
}