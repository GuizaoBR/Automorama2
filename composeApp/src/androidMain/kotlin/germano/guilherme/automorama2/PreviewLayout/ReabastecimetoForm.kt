package germano.guilherme.automorama2.PreviewLayout

import ReabastecimentoForm
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ui.theme.AutomoramaTheme

@Preview(showBackground = true)
@Composable
fun ReabastecimentoFormPreview(){
    AutomoramaTheme(true) {
        ReabastecimentoForm()
    }
}