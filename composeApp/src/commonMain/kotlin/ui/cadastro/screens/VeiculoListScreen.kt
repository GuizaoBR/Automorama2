package ui.cadastro.screens

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
import ui.Veiculos.VeiculosListScreen
import ui.Veiculos.VeiculosListUiState
import viewModelsFactory.VeiculosViewModelFactory

data class VeiculoListScreen(
    private val onFormClicked: () -> Unit = {},
    private val onFormFinished: () -> Unit = {},
    private val modifier: Modifier = Modifier
) : Screen, KoinComponent {
    private val veiculosViewModelFactory: VeiculosViewModelFactory by inject()

    @Composable
    override fun Content() {
        val navigator: Navigator = LocalNavigator.currentOrThrow
        val viewModel = remember {
            veiculosViewModelFactory.create()
        }
        val uiState by viewModel.uiState.collectAsState(VeiculosListUiState())
        VeiculosListScreen(
            uiState = uiState,
            onNewVeiculoClick = {
                onFormClicked()
                navigator.push(VeiculoFormScreen(onFormFinished= onFormFinished))

            },
            onVeiculoClick = {
                onFormClicked()
                navigator.push(VeiculoFormScreen(it.id, onFormFinished))

            },
            modifier = modifier

        )
    }
}
