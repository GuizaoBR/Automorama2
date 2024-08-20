package ui.drawerMenu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import kotlinx.coroutines.Job
import ui.cadastro.NavigationItem


@Composable
fun DrawerMenuItens(
    itens: List<NavigationItem>,
    drawerState: () -> Job
) {

    itens.forEach { item ->
        val navigator = LocalNavigator.current!!
        Row(
            modifier = Modifier.padding(8.dp)
                .fillMaxWidth()
                .clickable {
                    if (navigator.lastItem.key == item.screen.key) return@clickable
                    if(navigator.size > 1) navigator.pop() else navigator.push(item.screen)
                    drawerState()

                }
                .background(
                    if (navigator.lastItem.key == item.screen.key) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f) else Color.Transparent,
                    shape = RoundedCornerShape(50)
                )
                .padding(16.dp),
            content = { item.label() }
        )
    }
}
