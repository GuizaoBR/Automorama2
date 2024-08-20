package ui.cadastro

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen

data class NavigationItem (
    val name: String = "",
    val label: @Composable () -> Unit = {},
    val icon: @Composable () -> Unit = {},
    val modifier: Modifier = Modifier,
    val screen: Screen
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is NavigationItem) return false
        if (name != other.name) return false
        
        return true
    }
}