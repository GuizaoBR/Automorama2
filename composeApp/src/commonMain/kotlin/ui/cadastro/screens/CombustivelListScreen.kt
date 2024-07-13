package ui.cadastro.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
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

) : Screen, KoinComponent {

    @Composable
    override fun Content() {
        val combustiveisViewModelFactory: CombustiveisViewModelFactory by inject()
        val navigator: Navigator = LocalNavigator.currentOrThrow
        val viewModel = rememberScreenModel {
            combustiveisViewModelFactory.create()
        }
        val uiState by viewModel.uiState.collectAsState(CombustivelListUIState())
        CombustivelListScreen(
            uiState = uiState,
            onNewCombustivelClick = {
                navigator.push(CombustivelFormScreen())
            },
            onCombustivelClick = {
                navigator.push(CombustivelFormScreen(it.id))

            },

        )
    }
}
