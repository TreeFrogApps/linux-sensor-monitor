package com.treefrogapps.desktop.linux.sensor.monitor.resources

import java.util.*

object Resources : ResourceBundle() {

    private const val BUNDLE_FOLDER = "bundles"
    private val intResources = getBundle("$BUNDLE_FOLDER/integers")
    private val stringResources = getBundle("$BUNDLE_FOLDER/strings", Locale.getDefault())
    private val keys: List<String>
    private val keyMap: Map<String, Any>

    init {
        val stringList = stringResources.keys.toList()
        val strings = stringList.associateBy({ it }, { stringResources.getObject(it) })
        val integerList = intResources.keys.toList()
        val integers = integerList.associateBy({ it }, { intResources.getObject(it) })
        keys = stringList + integerList
        keyMap = strings + integers
    }

    override fun getKeys(): Enumeration<String> =
            keys.iterator().run {
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