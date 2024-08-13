package germano.guilherme.automorama2.PreviewLayout

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ui.theme.AutomoramaTheme
import ui.veiculoForm.VeiculoForm
import ui.veiculoForm.VeiculoFormUIState

@Preview
@Composable
fun VeiculoFormPreview(){
    AutomoramaTheme {
        VeiculoForm(
            uiState = VeiculoFormUIState(topAppBarTitle = "teste", isPlateValid = false),
            onSaveClick = {}
        )
    }
}

@Preview
@Composable
fun VeiculoFormPreviewDark(){
    AutomoramaTheme(true) {
        VeiculoForm(
            uiState = VeiculoFormUIState(topAppBarTitle = "teste", isPlateValid = false),
            onSaveClick = {}
        )
    }
}