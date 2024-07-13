package ui.recursos.reabastecimentos.screens

import ReabastecimentoForm
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import data.models.Veiculo
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.reabastecimentoForm.ReabastecimentoFormUIState
import viewModelsFactory.ReabastecimentoFormViewModelFactory

data class ReabastecimentoFormScreen(
    private val veiculoId: Long = 0,
    private val reabastecimentoId: Long? = null
) : Screen, KoinComponent {
    @Composable
    override fun Content() {
        val navigator: Navigator = LocalNavigator.currentOrThrow
        val viewModelFactory: ReabastecimentoFormViewModelFactory by inject()
        val viewModel = rememberScreenModel {
            viewModelFactory.create(reabastecimentoId, veiculoId)
        }
        val uiState by viewModel.uiState.collectAsState(ReabastecimentoFormUIState())
        ReabastecimentoForm(onBackClick = { navigator.pop() }, uiState = uiState, onSaveClick = {
            viewModel.saveReabastecimento()
            navigator.pop()
        } )
    }
}
