package germano.guilherme.automorama2.PreviewLayout

import App
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ui.drawerMenu.DrawerMenuItens
import ui.theme.AutomoramaTheme

@Preview
@Composable
fun DrawerMenuItensPreview(){
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    AutomoramaTheme(true) {
        App().DrawerMenu(drawerState = drawerState)
    }

}