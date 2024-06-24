package ui.drawerMenu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.cadastro.NavigationItem


@Composable
fun DrawerMenuItens(
    itens: List<NavigationItem>,
    modifier: Modifier = Modifier.padding(8.dp)
) {

    itens.forEach {
        Row(modifier= modifier
            .fillMaxWidth()
            .clickable(onClick = { it.onClick(it) }),
            content = {it.label()},

        )
        HorizontalDivider()
    }

}
