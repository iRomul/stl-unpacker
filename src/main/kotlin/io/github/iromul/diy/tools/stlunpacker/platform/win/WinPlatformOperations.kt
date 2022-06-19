package io.github.iromul.diy.tools.stlunpacker.platform.win

import io.github.iromul.diy.tools.stlunpacker.platform.PlatformOperations
import java.awt.SystemTray
import java.awt.Toolkit
import java.awt.TrayIcon
import java.awt.datatransfer.DataFlavor
import java.io.File
import java.nio.file.LinkOption
import java.util.concurrent.TimeUnit
import javax.imageio.ImageIO

class WinPlatformOperations : PlatformOperations {

    private val systemTray by lazy { SystemTray.getSystemTray() }

    override fun readClipboardText(): String? {
        val toolkit = Toolkit.getDefaultToolkit()
        val clipboard = toolkit.systemClipboard

        return if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
            clipboard.getData(DataFlavor.stringFlavor) as String
        } else {
            null
        }
    }

    override fun notify(caption: String, text: String) {
        val resource = WinPlatformOperations::class.java.getResource("/cura-stl-icon.png")

        val image = ImageIO.read(resource)

        val trayIcon = TrayIcon(image, "Downloader")
            .apply {
                isImageAutoSize = true
            }

        systemTray.add(trayIcon)

        trayIcon.displayMessage(caption, text, TrayIcon.MessageType.INFO)

        systemTray.remove(trayIcon)
    }

    override fun createThumbnail(modelDir: File) {
        modelDir.walkTopDown()
            .onFail { file, ioException -> System.err.println("Path unreachable: ${file}\n\t$ioException") }
            .filter {
                it.name.matches(".*\\.(jpg|png)".toRegex(RegexOption.IGNORE_CASE))
            }
            .take(1)
            .forEach { imageFile ->
                File(modelDir, "desktop.ini").apply {
                    writeText(
                        """
                        |[ViewState]
                        |FolderType=Generic
                        |Logo=${imageFile.absolutePath}
                        |
                    """.trimMargin("|")
                    )

                    setAttribute("dos:hidden" to true, LinkOption.NOFOLLOW_LINKS)
                    setAttribute("dos:system" to true, LinkOption.NOFOLLOW_LINKS)
                }
            }
    }

    override fun unzipHere(zipFile: File, targetDir: File, dirName: String) {
        ProcessBuilder("7z", "x", "-o$dirName", zipFile.absolutePath)
            .directory(targetDir)
            .start()
            .also { it.waitFor(10, TimeUnit.SECONDS) }
    }
}