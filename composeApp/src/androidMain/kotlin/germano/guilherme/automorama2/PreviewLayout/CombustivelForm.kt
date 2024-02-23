package germano.guilherme.automorama2.PreviewLayout

import CombustivelFormScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ui.combustivelForm.CombustivelFormUIState

@Preview
@Composable
fun PreviewCombustivelFormScreen() {
    CombustivelFormScreen(uiState = CombustivelFormUIState(nome = "SHELL V POWER"))
}