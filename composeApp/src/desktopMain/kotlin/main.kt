import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import data.models.Veiculo
import di.deviceInfoModule
import di.driverFactoryModule
import di.initKoin
import ui.Veiculos.VeiculosListScreen
import ui.Veiculos.VeiculosListUiState

fun main() = application {

    initKoin(listOf(driverFactoryModule, deviceInfoModule))

    Window(onCloseRequest = ::exitApplication, title = "Automorama2") {
        App().App()
    }
}


@Preview
@Composable
fun AppPreview() {
    App()
}
@Preview
@Composable
fun AppDesktopPreview() {
    val veiculos : MutableList<Veiculo> = emptyList<Veiculo>().toMutableList()

    repeat(20){
        veiculos.add(Veiculo(
            1,
            "teste",
            "teste",
            2021,
            2023,
            "abc1d23"
        ))
    }
    VeiculosListScreen(
        uiState = VeiculosListUiState(
            veiculos.toList()
        )
    )
}