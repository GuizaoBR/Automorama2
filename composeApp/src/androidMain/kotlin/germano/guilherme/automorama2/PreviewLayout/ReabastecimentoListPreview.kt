package germano.guilherme.automorama2.PreviewLayout

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ui.reabastecimentos.ReabastecimentoList
import ui.theme.AutomoramaTheme

@Composable
@Preview
fun ReabastecimentoListPreviewDark() {
    AutomoramaTheme(true) {
        ReabastecimentoList()
    }
}

@Composable
@Preview
fun ReabastecimentoListPreview() {
    AutomoramaTheme(false) {
        ReabastecimentoList()
    }
}