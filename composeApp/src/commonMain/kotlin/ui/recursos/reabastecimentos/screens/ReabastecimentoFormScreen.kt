package ui.recursos.reabastecimentos.screens

import ReabastecimentoForm
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import org.koin.core.component.KoinComponent

data class ReabastecimentoFormScreen(
    private val onFormClicked: () -> Unit = {},
    private val onFormFinished: () -> Unit = {},
    private val modifier: Modifier = Modifier
) : Screen, KoinComponent {

    @Composable
    override fun Content() {
        ReabastecimentoForm(modifier = modifier)
    }
}
