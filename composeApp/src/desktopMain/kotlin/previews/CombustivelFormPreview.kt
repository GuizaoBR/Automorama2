
package previews

import CombustivelFormScreen
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import ui.combustivelForm.CombustivelFormUIState

@Preview
@Composable
fun PreviewCombustivelFormScreen() {
    CombustivelFormScreen(uiState = CombustivelFormUIState(nome = "SHELL V POWER"))
}
