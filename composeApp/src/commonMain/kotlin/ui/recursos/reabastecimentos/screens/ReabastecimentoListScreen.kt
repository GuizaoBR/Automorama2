package ui.recursos.reabastecimentos.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.reabastecimentos.ReabastecimentoList
import ui.reabastecimentos.ReabastecimentoListUIState
import viewModelsFactory.ReabastecimentoListViewModelFactory

data class ReabastecimentoListScreen(
    private val onFormClicked: () -> Unit = {},
    private val onFormFinished: () -> Unit = {},
    private val modifier: Modifier = Modifier
) : Screen, KoinComponent {

    private val reabastecimentoViewModelFactory: ReabastecimentoListViewModelFactory by inject()
    private val viewModel = reabastecimentoViewModelFactory.create(0)
    @Composable
    override fun Content() {
        val navigator: Navigator = LocalNavigator.currentOrThrow

        val uiState by viewModel.uiState.collectAsState(ReabastecimentoListUIState())

        ReabastecimentoList(modifier = modifier, uiState = uiState) { veiculoid ->
            navigator.push(ReabastecimentoFormScreen(veiculoId = veiculoid, onBackClick = {
                navigator.pop()
            }))
        }
    }
}
