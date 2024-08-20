package ui.cadastro

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.LocalNavigator


@Composable
fun NavigationBar(
    itens: List<NavigationItem>,
    modifier: Modifier = Modifier
) {

    NavigationBar(modifier = modifier) {
        itens.forEach {
            val navigator = LocalNavigator.current!!
            NavigationBarItem(
                icon = it.icon,
                label = it.label,
                selected = it.screen.key == navigator.lastItem.key,
                onClick = {
                    if(it.screen.key == navigator.lastItem.key) return@NavigationBarItem
                    navigator.replace(it.screen)
                }
            )

        }
    }


}



