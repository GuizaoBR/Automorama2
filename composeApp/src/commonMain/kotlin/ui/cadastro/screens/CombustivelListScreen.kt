package ui.cadastro.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.Combustiveis.CombustivelListScreen
import ui.Combustiveis.CombustivelListUIState
import ui.Veiculos.VeiculosListScreen
import ui.Veiculos.VeiculosListUiState
import viewModelsFactory.CombustiveisViewModelFactory
import viewModelsFactory.VeiculosViewModelFactory

class CombustivelListScreen(
    private val onFormClicked: () -> Unit = {},
    private val onFormFinished: () -> Unit = {},
    private val modifier: Modifier = Modifier
) : Screen, KoinComponent {
    private val combustiveisViewModelFactory: CombustiveisViewModelFactory by inject()

    @Composable
    override fun Content() {
        val navigator: Navigator = LocalNavigator.currentOrThrow
        val viewModel = remember {
            combustiveisViewModelFactory.create()
        }
        val uiState by viewModel.uiState.collectAsState(CombustivelListUIState())
        CombustivelListScreen(
            uiState = uiState,
            onNewCombustivelClick = {
                onFormClicked()
                navigator.push(CombustivelFormScreen(onFormFinished= onFormFinished))
            },
            onCombustivelClick = {
                onFormClicked()
                navigator.push(CombustivelFormScreen(it.id, onFormFinished= onFormFinished))

            },
            modifier = modifier

        )
    }
}
