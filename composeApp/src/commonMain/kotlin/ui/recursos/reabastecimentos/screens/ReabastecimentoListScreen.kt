package ui.recursos.reabastecimentos.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.reabastecimentos.ReabastecimentoList
import ui.reabastecimentos.ReabastecimentoListUIState
import viewModelsFactory.ReabastecimentoListViewModelFactory

class ReabastecimentoListScreen : Screen, KoinComponent {


    @Composable
    override fun Content() {
        val navigator: Navigator = LocalNavigator.currentOrThrow
        val reabastecimentoViewModelFactory: ReabastecimentoListViewModelFactory by inject()
        val viewModel = rememberScreenModel {reabastecimentoViewModelFactory.create() }


        val uiState by viewModel.uiState.collectAsState(ReabastecimentoListUIState())


        ReabastecimentoList(uiState = uiState,
        editReabastecimento = { veiculoId, reabastecimentoId ->
            navigator.push(ReabastecimentoFormScreen(veiculoId = veiculoId, reabastecimentoId = reabastecimentoId))
        }
        ) { veiculoid ->
            navigator.push(ReabastecimentoFormScreen(veiculoId = veiculoid))
        }
    }


}
