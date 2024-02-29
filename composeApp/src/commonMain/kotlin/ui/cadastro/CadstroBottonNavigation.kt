package ui.cadastro

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator



@Composable
fun NavigationBar(selectedItem: String = "",
                onCombustivelClick: () -> Unit = {},
                  onVeiculoClick: () -> Unit = {},
                  modifier: Modifier = Modifier) {

    NavigationBar(modifier = modifier) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.DirectionsCar, contentDescription = null) },
            label = { Text("Veiculos") },
            selected = selectedItem == "Veiculos",
            onClick = onVeiculoClick
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.LocalGasStation, contentDescription = null) },
            label = { Text("Combustiveis") },
            selected = selectedItem == "Combustivel",
            onClick = onCombustivelClick
        )
    }
//    NavigationBar {
//        TabNavigationItem(VeiculoTab)
//        TabNavigationItem(CombustivelTab)
//    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator: TabNavigator = LocalTabNavigator.current

    NavigationBarItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = {
            tab.options.icon?.let { icon ->
                Icon(
                    painter = icon,
                    contentDescription =
                    tab.options.title
                )
            }
        },
        label = {
            Text(
                text = tab.options.title
            )
        }
    )
}

