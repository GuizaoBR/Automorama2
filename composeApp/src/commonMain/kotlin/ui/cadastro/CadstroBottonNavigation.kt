package ui.cadastro

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun NavigationBar(selectedItem: String,
                onCombustivelClick: () -> Unit = {},
                  onVeiculoClick: () -> Unit = {}) {


    NavigationBar {
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
}