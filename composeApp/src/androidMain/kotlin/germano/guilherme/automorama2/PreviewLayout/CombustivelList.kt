package germano.guilherme.automorama2.PreviewLayout

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import data.models.Combustivel
import data.models.Veiculo
import ui.Combustiveis.CombustivelListScreen
import ui.Combustiveis.CombustivelListUIState

@Preview
@Composable
fun CombustivelListPrevuew(){
    val combustiveis : MutableList<Combustivel> = emptyList<Combustivel>().toMutableList()

    repeat(20){
        combustiveis.add(
           Combustivel(nome= "SHELL V POWER")
        )
    }
    Surface {
            CombustivelListScreen(uiState = CombustivelListUIState(combustiveis))

    }
}