package germano.guilherme.automorama2.PreviewLayout

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import data.models.Combustivel
import ui.combustiveis.CombustivelListScreen
import ui.combustiveis.CombustivelListUIState
import ui.theme.AutomoramaTheme

@Preview
@Composable
fun CombustivelListPreview(){
    val combustiveis : MutableList<Combustivel> = emptyList<Combustivel>().toMutableList()

    repeat(20){
        combustiveis.add(
           Combustivel(nome= "Gasolina Premium")
        )
    }
    AutomoramaTheme(true) {
            CombustivelListScreen(uiState = CombustivelListUIState(combustiveis))

    }
}