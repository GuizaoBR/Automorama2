package ui.cadastro

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun NavigationBar(
    itens: List<NavigationItem>,
    selectedItem: NavigationItem,
    modifier: Modifier = Modifier
) {

    NavigationBar(modifier = modifier) {
        itens.forEach {
            NavigationBarItem(
                icon = it.icon,
                label = it.label,
                selected = it == selectedItem,
                onClick = { it.onClick(it) }
            )

        }
    }


}



