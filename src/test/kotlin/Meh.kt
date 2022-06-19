import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.awt.Desktop
import java.awt.SystemTray
import java.awt.Toolkit
import java.awt.TrayIcon

class Meh {

    @Test
    @Disabled
    fun `test sum`() {
        val desktop = Desktop.getDesktop()

        if (desktop.isSupported(Desktop.Action.BROWSE)) {
//            desktop.browse(URL("http://google.com").toURI())
        }

        val tray = SystemTray.getSystemTray()

        val image = Toolkit.getDefaultToolkit().createImage("cura-stl-icon.png")

        val trayIcon = TrayIcon(image, "Icon Demo")
            .apply {
                isImageAutoSize = true
                toolTip = "Icon Demo Tooltip"
            }

        tray.add(trayIcon)

        trayIcon.displayMessage("Hello", "World", TrayIcon.MessageType.ERROR)
        trayIcon.displayMessage("Wow", "Super!", TrayIcon.MessageType.INFO)
    }
}