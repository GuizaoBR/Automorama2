package ui.cadastro.screens

import CombustivelFormScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.combustivelForm.CombustivelFormUIState
import viewModelsFactory.CombustivelFormViewModelFactory

data class CombustivelFormScreen(
    private val id: Long? = null,

) : Screen, KoinComponent {

    @Composable
    override fun Content() {

        val combustivelFormViewModelFactory: CombustivelFormViewModelFactory by inject()
        val navigator: Navigator = LocalNavigator.currentOrThrow
        val viewModel = rememberScreenModel {
            combustivelFormViewModelFactory.create(id)
        }
        val uiState by viewModel.uiState.collectAsState(CombustivelFormUIState())
        CombustivelFormScreen(
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
