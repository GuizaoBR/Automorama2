package ui.cadastro.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


import ui.cadastro.GroupCadastros

 class GroupCadastrosScreen: Screen, KoinComponent {

    @Composable
    override fun Content() {
        val resolution: Pair<Int, Int> by inject()

        GroupCadastros(
        resolution = resolution
        )

    }
}
