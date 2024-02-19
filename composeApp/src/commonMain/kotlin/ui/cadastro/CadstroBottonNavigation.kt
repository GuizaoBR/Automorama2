package ui.cadastro

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun NavigationBar(onCombustivelClick: () -> Unit = {},
                  onVeiculoClick: () -> Unit = {}) {
    var selectedItem by remember { mutableStateOf("Veiculos") }

    NavigationBar {
        NavigationBarItem(
            icon = {  },
            label = { Text("Veiculos") },
            selected = true,
            onClick = onVeiculoClick
        )
        NavigationBarItem(
            icon = {  },
            label = { Text("Combustiveis") },
            selected = false,
            onClick = onCombustivelClick
        )
    }
}