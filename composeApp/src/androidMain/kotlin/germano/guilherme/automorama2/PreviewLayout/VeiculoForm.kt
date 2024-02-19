package germano.guilherme.automorama2.PreviewLayout

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ui.VeiculoForm.VeiculoForm
import ui.VeiculoForm.VeiculoFormUIState

@Preview
@Composable
fun VeiculoFormPreview(){
    Surface {
        VeiculoForm(
            uiState = VeiculoFormUIState(),
            onSaveClick = {}
        )
    }
}