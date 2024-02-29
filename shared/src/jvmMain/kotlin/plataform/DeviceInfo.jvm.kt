package plataform

import java.awt.Dimension
import java.awt.Toolkit


actual class DeviceInfo {
    actual fun getScreenResolution(): Pair<Int, Int> {
        val screenSize: Dimension = Toolkit.getDefaultToolkit().screenSize
        return Pair(screenSize.width, screenSize.height)

    }
}