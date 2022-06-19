package io.github.iromul.commons

object Platform {

    private val os = System.getProperty("os.name")

    val isWindows: Boolean
        get() = os.startsWith("Windows")

    val isMac: Boolean
        get() = os.startsWith("Mac")

    val isLinux: Boolean
        get() = os.startsWith("Linux")
}