package ui.drawerMenu

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DrawerMenuItens(
    modifier: Modifier = Modifier.padding(8.dp)
) {
    val typography = Typography(
        titleMedium = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            lineHeight =  28.sp
        )
    )
    Row(modifier = modifier
        .fillMaxWidth()) {
        Icon(Icons.Default.Add, contentDescription = "Cadastro", modifier = Modifier.padding(start = 16.dp, top = 6.dp) )
        Spacer(modifier = Modifier.padding(start = 32.dp))
        Text("Cadastro",
            fontWeight = typography.titleMedium.fontWeight,
            fontSize = typography.titleMedium.fontSize,
            lineHeight = typography.titleMedium.lineHeight
        )
    }
    Divider()
    Row(modifier = modifier
        .fillMaxWidth(),
        ) {
        Icon(Icons.Default.Payments, contentDescription = "Gastos", modifier = Modifier.padding(start = 16.dp, top = 6.dp) )
        Spacer(modifier = Modifier.padding(start = 32.dp))
        Text("Gastos",
            fontWeight = typography.titleMedium.fontWeight,
            fontSize = typography.titleMedium.fontSize,
            lineHeight = typography.titleMedium.lineHeight,
            modifier= Modifier.fillMaxWidth()
        )
    }
}
