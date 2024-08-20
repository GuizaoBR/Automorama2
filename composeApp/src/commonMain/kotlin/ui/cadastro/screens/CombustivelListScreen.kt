package ui.cadastro.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.combustiveis.CombustivelListScreen
import ui.combustiveis.CombustivelListUIState
import viewModelsFactory.CombustiveisViewModelFactory

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
