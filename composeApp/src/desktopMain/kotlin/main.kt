import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import data.DriverFactory
import data.models.Veiculo
import ui.Veiculos.VeiculosListScreen
import ui.Veiculos.VeiculosListUiState

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Automorama2") {
        App(
            DriverFactory().createDriver()
        )
    }
}

@Preview
@Composable
fun AppDesktopPreview() {
    var veiculos : MutableList<Veiculo> = emptyList<Veiculo>().toMutableList()

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