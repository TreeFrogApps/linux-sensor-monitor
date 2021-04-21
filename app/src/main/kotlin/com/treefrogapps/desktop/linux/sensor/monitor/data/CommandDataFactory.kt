package com.treefrogapps.desktop.linux.sensor.monitor.data

import java.io.InputStreamReader
import java.util.function.Supplier

abstract class CommandDataFactory<T> : Supplier<T> {

    companion object {
        @JvmStatic private val LINE_SEPARATOR: String by lazy { System.getProperty("line.separator") }
    }

    protected abstract fun command(): String

    protected abstract fun adapt(output: String): T

    override fun get(): T =
        Runtime.getRuntime()
            .exec(command())
            .let(Process::getInputStream)
            .let(::InputStreamReader)
            .let { isr -> isr.useLines { lines -> lines.joinToString(LINE_SEPARATOR) } }
            .let(this::adapt)
}