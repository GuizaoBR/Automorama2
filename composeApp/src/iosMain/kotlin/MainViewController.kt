import androidx.compose.ui.window.ComposeUIViewController
import di.deviceInfoModule
import di.driverFactoryModule
import di.initKoin

fun MainViewController() = ComposeUIViewController { App().App() }

fun initKoin() {
    initKoin(listOf(driverFactoryModule, deviceInfoModule))

}