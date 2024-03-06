import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.cadastro.NavigationBar
import ui.cadastro.NavigationItem
import ui.cadastro.screens.CombustivelListScreen
import ui.cadastro.screens.VeiculoListScreen
import ui.drawerMenu.DrawerMenuItens

class App() : KoinComponent {


    val resolution: Pair<Int, Int> by inject()

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview
    @Composable
    fun App() {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        MaterialTheme {
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet {
                        //DrawerMenuItens()
                        val typography = Typography(
                            titleMedium = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp,
                                lineHeight =  28.sp
                            )
                        )
                        val cadastroItem = NavigationItem(
                            name = "Cadastro",
                            label = {
                                Icon(Icons.Default.Add, contentDescription = "Cadastro", modifier = Modifier.padding(start = 16.dp, top = 6.dp) )
                                Spacer(modifier = Modifier.padding(start = 32.dp))
                                Text("Cadastro",
                                     fontWeight = typography.titleMedium.fontWeight,
                                     fontSize = typography.titleMedium.fontSize,
                                     lineHeight = typography.titleMedium.lineHeight
                                )
                            },
                            onClick = {

                            },
                            modifier = Modifier.padding(start = 16.dp, top = 6.dp)
                        )
                        val gastosItem = NavigationItem(
                            name = "Gastos",
                            label = {
                                Icon(Icons.Default.Payments, contentDescription = "Gastos", modifier = Modifier.padding(start = 16.dp, top = 6.dp) )
                                Spacer(modifier = Modifier.padding(start = 32.dp))
                                Text("Gastos",
                                     fontWeight = typography.titleMedium.fontWeight,
                                     fontSize = typography.titleMedium.fontSize,
                                     lineHeight = typography.titleMedium.lineHeight
                                )
                                    },
                            onClick = {

                            },
                            modifier = Modifier.padding(start = 16.dp, top = 6.dp)
                        )
                        DrawerMenuItens(listOf(cadastroItem, gastosItem))
                    }
                }
            ) {

                var showBottom by remember { mutableStateOf(true) }

                val offset by animateIntOffsetAsState(
                    targetValue = if (showBottom) {
                        IntOffset.Zero
                    } else {
                        IntOffset(convertDpToPx(resolution.second.toFloat()) * -1, 0)
                    },
                    label = "offset"
                )
                Navigator(
                    screen = ShowVeiculoListScreen(onFormClicked = {
                        showBottom = false
                    },
                        onFormFinished = {
                            showBottom = true
                        })
                ) { navigator ->
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text("AUTOMORAMA")
                                },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        scope.launch {
                                            drawerState.apply {
                                                if (isClosed) open() else close()
                                            }
                                        }
                                    }) {
                                        Icon(
                                            imageVector = Icons.Filled.Menu,
                                            contentDescription = "Localized description"
                                        )
                                    }
                                }
                            )
                        },
                        bottomBar = {
                            var selectedItem by remember { mutableStateOf(NavigationItem(
                                name = "Veículos",
                                label = { Text("Veículos") },
                                icon = { Icon(Icons.Default.DirectionsCar, contentDescription = null) },
                                onClick = {
                                }
                            )) }
                            val combustivelItem = NavigationItem(
                                name = "Combustíveis",
                                label = { Text("Combustíveis") },
                                icon = { Icon(Icons.Default.LocalGasStation, contentDescription = null) },
                                onClick = {
                                    if(selectedItem == it) return@NavigationItem
                                    selectedItem = it
                                    navigator.replace(
                                        CombustivelListScreen(
                                            onFormClicked = {
                                                showBottom = false
                                                            },
                                            onFormFinished = {
                                                showBottom = true
                                                             },
                                            modifier = Modifier.padding(top = 60.dp, bottom = 80.dp)
                                        )
                                    )
                                }
                            )

                            val veiculoItem = NavigationItem(
                                name = "Veículos",
                                label = { Text("Veículos") },
                                icon = { Icon(Icons.Default.DirectionsCar, contentDescription = null) },
                                onClick = {
                                    if(selectedItem == it) return@NavigationItem
                                    selectedItem = it
                                    navigator.replace(
                                        VeiculoListScreen(
                                            onFormClicked = {
                                                showBottom = false
                                                            },
                                            onFormFinished = {
                                                showBottom = true
                                                             },
                                            modifier = Modifier.padding(top = 60.dp, bottom = 80.dp)
                                        )
                                    )
                                }
                            )
                            NavigationBar(
                                itens = listOf(veiculoItem, combustivelItem),
                                selectedItem = selectedItem,
                                modifier = Modifier
                                    .offset {
                                        offset
                                    }
                            )

                        }

                    ) {
                        SlideTransition(navigator)
                    }
                }
            }
        }
    }

    @Composable
    fun convertDpToPx(dp: Float): Int {
        val density = LocalDensity.current.density
        return (dp * density).toInt()
    }


    fun ShowVeiculoListScreen(onFormClicked: () -> Unit = {}, onFormFinished: () -> Unit = {}): Screen {
        return VeiculoListScreen(
            onFormClicked = onFormClicked,
            onFormFinished = onFormFinished,
            modifier = Modifier.padding(top = 60.dp, bottom = 80.dp)
        )
    }


}

