package ui.cadastro.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.VeiculoForm.VeiculoForm
import ui.VeiculoForm.VeiculoFormUIState
import viewModelsFactory.VeiculoFormViewModelFactory

data class VeiculoFormScreen(
    private val id: Long? = null
) : Screen, KoinComponent {

    @Composable
    override fun Content() {
        val veiculoFormViewModelFactory: VeiculoFormViewModelFactory by inject()

        val navigator: Navigator = LocalNavigator.currentOrThrow
        val viewModel = rememberScreenModel {
            veiculoFormViewModelFactory.create(id)
        }
        val uiState by viewModel.uiState.collectAsState(VeiculoFormUIState())
        VeiculoForm(
            uiState = uiState,
            onSaveClick = {
                viewModel.save()
                navigator.pop()
            },
            onBackClick = {
                navigator.pop()
            },
        )
    }
}
