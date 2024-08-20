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
import ui.veiculos.VeiculosListScreen
import ui.veiculos.VeiculosListUiState
import ui.recursos.reabastecimentos.screens.ReabastecimentoListScreen
import viewModelsFactory.VeiculosViewModelFactory

class VeiculoListScreen(

) : Screen, KoinComponent {


    @Composable
    override fun Content() {
        val veiculosViewModelFactory: VeiculosViewModelFactory by inject()
        val navigator: Navigator = LocalNavigator.currentOrThrow

        val viewModel = rememberScreenModel {
            veiculosViewModelFactory.create()
        }
        val uiState by viewModel.uiState.collectAsState(VeiculosListUiState())
        VeiculosListScreen(
            uiState = uiState,
            onNewVeiculoClick = {
                navigator.push(VeiculoFormScreen())

            },
            onVeiculoClick = {
                navigator.parent?.push(ReabastecimentoListScreen(it.id))

            },
            onEditVeiculoClick = {
                navigator.push(VeiculoFormScreen(it.id))
            }

        )
    }
}
