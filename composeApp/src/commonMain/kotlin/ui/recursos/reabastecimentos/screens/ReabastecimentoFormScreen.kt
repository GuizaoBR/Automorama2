package ui.recursos.reabastecimentos.screens

import ReabastecimentoForm
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import data.models.Veiculo
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.reabastecimentoForm.ReabastecimentoFormUIState
import viewModelsFactory.ReabastecimentoFormViewModelFactory

data class ReabastecimentoFormScreen(
    private val veiculoId: Long = 0,
    private val onBackClick: () -> Unit = {},
    private val modifier: Modifier = Modifier
) : Screen, KoinComponent {
    private val viewModelFactory: ReabastecimentoFormViewModelFactory by inject()
    @Composable
    override fun Content() {
        val viewModel = remember {
            viewModelFactory.create(null, veiculoId)
        }
        val uiState by viewModel.uiState.collectAsState(ReabastecimentoFormUIState())
        ReabastecimentoForm(modifier = modifier, onBackClick = onBackClick, uiState = uiState, onSaveClick = {
            viewModel.saveReabastecimento()
            onBackClick()
        } )
    }
}
