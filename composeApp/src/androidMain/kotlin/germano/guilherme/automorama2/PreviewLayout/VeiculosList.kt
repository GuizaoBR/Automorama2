package germano.guilherme.automorama2.PreviewLayout

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import data.models.Veiculo
import ui.Veiculos.VeiculosListScreen
import ui.Veiculos.VeiculosListUiState
import ui.theme.AutomoramaTheme

@Preview
@Composable
fun VeiculoListPreview() {

    val veiculos : MutableList<Veiculo> = emptyList<Veiculo>().toMutableList()

    repeat(20){
        veiculos.add(
            Veiculo(
            1,
            "teste",
            "teste",
            2021,
            2023,
            "abc1d23",
                "teste",
                10.0
        )
        )
    }
    AutomoramaTheme(true) {
        VeiculosListScreen(
            uiState = VeiculosListUiState(
                veiculos.toList()
            )
        )
    }
}
