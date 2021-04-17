package com.treefrogapps.desktop.linux.sensor.monitor.resources

import java.util.*
import kotlin.LazyThreadSafetyMode.NONE

object Resources : ResourceBundle() {

    private const val BUNDLE_FOLDER = "bundles"
    private val intResources by lazy(NONE) { getBundle("$BUNDLE_FOLDER/integers") }
    private val stringResources by lazy(NONE) { getBundle("$BUNDLE_FOLDER/strings", Locale.getDefault()) }
    private val keyMap: Map<String, Any> by lazy {
        stringResources.keys.toList().associateBy({ it }, { stringResources.getObject(it) }) +
                intResources.keys.toList().associateBy({ it }, { intResources.getObject(it) })
    }

    override fun getKeys(): Enumeration<String> =
        keyMap.keys.iterator().run {
            object : Enumeration<String> {
                override fun hasMoreElements(): Boolean = hasNext()
                override fun nextElement(): String = next()
            }
        }

    override fun handleGetObject(p0: String): Any = keyMap[p0]
        ?: throw NullPointerException("value associated with key $p0 is null")

    @JvmStatic fun ResourceBundle.getInteger(key: String): Int = getString(key).toInt()
    @JvmStatic fun ResourceBundle.getStringList(key: String, delimiter: String = ","): List<String> = getString(key).split(delimiter)
}