import ViewModels.CombustiveisViewModel
import ViewModels.CombustivelFormViewModel
import ViewModels.VeiculoFormViewModel
import ViewModels.VeiculoViewModel
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.sqldelight.db.SqlDriver
import data.repositories.CombustivelRepository
import data.repositories.VeiculoRepository
import kotlinx.coroutines.*
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.Combustiveis.CombustivelListScreen
import ui.Combustiveis.CombustivelListUIState
import ui.VeiculoForm.VeiculoForm
import ui.Veiculos.VeiculosListScreen
import ui.Veiculos.VeiculosListUiState
import ui.cadastro.NavigationBar

@OptIn(ExperimentalResourceApi::class, ExperimentalLayoutApi::class)
@Composable
fun App(sqlDriver: SqlDriver) {
    PreComposeApp {
        val veiculoRepository = VeiculoRepository(sqlDriver)
        val combustivelRepository = CombustivelRepository(sqlDriver)
        val navigator = rememberNavigator()
        var selectedMenuCadastro by remember { mutableStateOf("Veiculos") }
        Box(modifier = Modifier.fillMaxSize()) {
            FlowColumn(modifier = Modifier.align(Alignment.BottomEnd)) {
                NavigationBar(
                    selectedMenuCadastro,
                    onVeiculoClick = {
                        selectedMenuCadastro = "Veiculos"
                        navigator.navigate("veiculosList")
                                     },
                    onCombustivelClick = {
                        selectedMenuCadastro = "Combustivel"
                        navigator.navigate("combustivelList")
                    }

                )
            }
            NavHost(
                navigator = navigator,
                initialRoute = "cadastro",
                navTransition = NavTransition()
            ) {

                group(route = "cadastro", initialRoute = "veiculosList") {
                    scene(
                        "veiculosList",
                        navTransition = NavTransition(createTransition = slideInHorizontally())
                    ) {
                        val viewModel = remember {
                            VeiculoViewModel(veiculoRepository)
                        }
                        val uiState by viewModel.uiState.collectAsState(VeiculosListUiState())
                        VeiculosListScreen(
                            uiState = uiState,
                            onNewVeiculoClick = {
                                navigator.navigate("veiculoForm")
                            },
                            onVeiculoClick = {
                                navigator.navigate("veiculoForm/${it.id}")

                            },

                            modifier = Modifier.padding(bottom = 80.dp)

                        )
                    }
                    scene(
                        "veiculoForm/{id}?",
                        navTransition = NavTransition()
                    ) {
                        val id: Long? = it.path<Long>("id")
                        val viewModel = remember {
                            VeiculoFormViewModel(veiculoRepository, id)
                        }
                        val uiState by viewModel.uiState.collectAsState()
                        VeiculoForm(
                            uiState = uiState,
                            onSaveClick = {
                                viewModel.save()
                                navigator.goBack()
                            }
                        )
                    }
                    scene(
                        "combustivelList",
                        navTransition = NavTransition(createTransition = slideInHorizontally())
                    ) {
                        val viewModel = remember {
                            CombustiveisViewModel(combustivelRepository)
                        }
                        val uiState by viewModel.uiState.collectAsState(CombustivelListUIState())
                        CombustivelListScreen(
                            uiState = uiState,
                            modifier = Modifier.padding(bottom = 80.dp),
                            onNewCombustivelClick = {
                                navigator.navigate("combustivelForm")
                            },
                            onCombustivelClick = {
                                navigator.navigate("combustivelForm/${it.id}")
                            },


                            )

                    }
                    scene(
                        "combustivelForm/{id}?",
                        navTransition = NavTransition()
                    ) {
                        val id: Long? = it.path<Long>("id")
                        val viewModel = remember {
                            CombustivelFormViewModel(combustivelRepository, id)
                        }
                        val uiState by viewModel.uiState.collectAsState()
                        CombustivelFormScreen(
                            uiState = uiState,
                            onSaveClick = {
                                viewModel.save()
                                navigator.goBack()
                            },
                            onBackClick = {
                                navigator.goBack()
                            }
                        )
                    }
                }
            }
        }
    }
}
