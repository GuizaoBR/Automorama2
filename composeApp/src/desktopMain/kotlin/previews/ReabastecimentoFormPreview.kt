package previews

import ReabastecimentoForm
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import ui.theme.AutomoramaTheme

@Preview()
@Composable
fun ReabastecimentoFormPreview(){
    AutomoramaTheme(true) {
        ReabastecimentoForm()
    }
}