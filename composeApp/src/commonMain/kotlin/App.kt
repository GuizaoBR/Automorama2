import ViewModels.VeiculoViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import app.cash.sqldelight.db.SqlDriver
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.VeiculoForm.VeiculoForm
import ui.Veiculos.VeiculosListScreen
import ui.Veiculos.VeiculosListUiState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App(sqlDriver: SqlDriver) {
    PreComposeApp {
        val navigator = rememberNavigator()
        NavHost(
            navigator = navigator,
            initialRoute = "veiculosList",
            navTransition = NavTransition()
        ){
            scene(
                "veiculosList",
                navTransition = NavTransition()
            ){
                val viewModel = remember {
                    VeiculoViewModel(sqlDriver)
                }
                val uiState by viewModel.uiState.collectAsState(VeiculosListUiState())
                VeiculosListScreen(
                    uiState = uiState,
                    onNewVeiculoClick = {
                                        navigator.navigate("veiculoForm")

                    },
                    onVeiculoClick = {

                    }

                )
            }
            scene(
                "veiculoForm/{id}?",
                navTransition = NavTransition()
            ) {
                VeiculoForm()
            }
        }
    }
}