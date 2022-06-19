package io.github.iromul.diy.tools.stlunpacker

import io.github.iromul.commons.Platform
import io.github.iromul.diy.tools.stlunpacker.download.ThingiverseClient
import io.github.iromul.diy.tools.stlunpacker.platform.NoOpPlatformOperations
import io.github.iromul.diy.tools.stlunpacker.platform.win.WinPlatformOperations

object ApplicationContext {

    private val platformOperations by lazy {
        when {
            Platform.isWindows -> WinPlatformOperations()
            else -> NoOpPlatformOperations()
        }
    }

    private val thingiverseClient by lazy {
        ThingiverseClient()
    }

    val unpackerService by lazy {
        UnpackerService(platformOperations, thingiverseClient)
    }
}