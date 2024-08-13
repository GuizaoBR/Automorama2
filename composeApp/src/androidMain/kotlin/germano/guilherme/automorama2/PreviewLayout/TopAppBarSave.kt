package germano.guilherme.automorama2.PreviewLayout

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ui.theme.AutomoramaTheme
import ui.topAppBar.TopAppBarSave

@Preview(showBackground = true)
@Composable
fun TopAppBarSavePreviewNotValid(){
    AutomoramaTheme {
        TopAppBarSave(title = "teste", onBack = { /*TODO*/ }, onSave = { /*TODO*/ }, isValid = false)
    }
}

@Preview(showBackground = true)
@Composable
fun TopAppBarSavePreviewValid(){
    AutomoramaTheme {
        TopAppBarSave(title = "teste", onBack = { /*TODO*/ }, onSave = { /*TODO*/ }, isValid = true)
    }
}


@Preview(showBackground = true)
@Composable
fun TopAppBarSavePreviewNotValidDark(){
    AutomoramaTheme(true) {

        TopAppBarSave(title = "teste", onBack = { /*TODO*/ }, onSave = { /*TODO*/ }, isValid = false)
    }
}

@Preview(showBackground = true)
@Composable
fun TopAppBarSavePreviewValidDark(){
    AutomoramaTheme(true) {

        TopAppBarSave(title = "teste", onBack = { /*TODO*/ }, onSave = { /*TODO*/ }, isValid = true)
    }
}