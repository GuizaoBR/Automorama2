package ui.cadastro.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
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
    private val id: Long? = null,
    private val onFormFinished:  () -> Unit = {},
    private val modifier: Modifier = Modifier
) : Screen, KoinComponent {
    private val veiculoFormViewModelFactory: VeiculoFormViewModelFactory by inject()

    @Composable
    override fun Content() {
        LifecycleEffect(
            onDisposed = onFormFinished
        )
        val navigator: Navigator = LocalNavigator.currentOrThrow
        val viewModel = remember {
            veiculoFormViewModelFactory.create(id)
        }
        val uiState by viewModel.uiState.collectAsState(VeiculoFormUIState())
        VeiculoForm(
            uiState = uiState,
            onSaveClick = {
                viewModel.save()
                navigator.pop()
            },
            modifier = modifier.padding(top = 60.dp)

        )
    }
}
