package ui.recursos.reabastecimentos.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import org.koin.core.component.KoinComponent
import ui.reabastecimentos.ReabastecimentoList

data class ReabastecimentoListScreen(
    private val onFormClicked: () -> Unit = {},
    private val onFormFinished: () -> Unit = {},
    private val modifier: Modifier = Modifier
) : Screen, KoinComponent {

    @Composable
    override fun Content() {
        ReabastecimentoList(modifier = modifier)
    }
}
