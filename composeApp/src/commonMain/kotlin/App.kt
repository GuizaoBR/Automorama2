import ViewModels.CombustivelFormViewModel
import ViewModels.VeiculoFormViewModel
import ViewModels.VeiculoViewModel
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.sqldelight.db.SqlDriver
import data.repositories.CombustivelRepository
import data.repositories.VeiculoRepository
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.Combustiveis.CombustivelListScreen
import ui.Combustiveis.CombustivelListUIState
import ui.VeiculoForm.VeiculoForm
import ui.Veiculos.VeiculosListScreen
import ui.Veiculos.VeiculosListUiState
import ui.cadastro.NavigationBar
import viewModelsFactory.CombustiveisViewModelFactory
import viewModelsFactory.CombustivelFormViewModelFactory

class App(private val sqlDriver: SqlDriver): KoinComponent {

    private val combustiveisViewModelFactory: CombustiveisViewModelFactory by inject()
    private val combustivelFormViewModelFactory: CombustivelFormViewModelFactory by inject()

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    fun MyApp() {
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
                                combustiveisViewModelFactory.create()
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
//                                CombustivelFormViewModel(combustivelRepository, id)
                                combustivelFormViewModelFactory.create(id)
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
}
