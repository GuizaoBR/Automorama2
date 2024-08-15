package ui.drawerMenu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import ui.cadastro.NavigationItem


@Composable
fun DrawerMenuItens(
    itens: List<NavigationItem>,
    modifier: Modifier = Modifier.padding(8.dp),
    selectedItem: NavigationItem
) {

    itens.forEach {
        Row(modifier= modifier
            .fillMaxWidth()
            .clickable(onClick = {
                it.onClick(it)
            })
            .background(
                if(selectedItem == it) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f) else Color.Transparent,
                shape = RoundedCornerShape(50)
            )
            .padding(16.dp),
            content = {it.label()},

        )
    }
}
