package ui.cadastro.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen


import ui.cadastro.GroupCadastros

data class GroupCadastrosScreen(
    private val modifier: Modifier = Modifier,
    private val resolution: Pair<Int, Int>
) : Screen {

    @Composable
    override fun Content() {
        GroupCadastros(
            resolution = resolution,
            modifier = modifier
        )

    }
}
