import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.ScaleTransition
import ui.theme.AutomoramaTheme
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.cadastro.NavigationItem
import ui.cadastro.screens.GroupCadastrosScreen
import ui.recursos.reabastecimentos.screens.ReabastecimentoListScreen
import ui.drawerMenu.DrawerMenuItens
import ui.recursos.reabastecimentos.screens.ReabastecimentoFormScreen

class App() : KoinComponent {


    private val resolution: Pair<Int, Int> by inject()

    @Preview
    @Composable
    fun App(modifier: Modifier = Modifier) {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        AutomoramaTheme{
            Navigator(
                screen = ShowGroupScreen(modifier = modifier)
            ) { navigator ->
                val onFormClicked = {
                    navigator.push(ReabastecimentoFormScreen())
                }

                /* Pass navigator to ModalNavigationDrawer and subsequently to DrawerMenu */
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        DrawerMenu(navigator, drawerState, modifier = modifier)
                    }
                ) {
                    Scaffold(
                        modifier = modifier.statusBarsPadding(),
                        topBar = {
                        TopBar(drawerState)
                    },
                    ) {
                        ScaleTransition(navigator, modifier = modifier.padding(it))
                    }
                }
            }
        }
    }




    fun ShowGroupScreen(onFormClicked: () -> Unit = {}, onFormFinished: () -> Unit = {}, modifier: Modifier): Screen {
        return GroupCadastrosScreen(resolution = resolution, modifier = modifier)
    }

    @Composable
    @Preview
    fun DrawerMenu(navigator: Navigator, drawerState: DrawerState, modifier: Modifier) {
        val scope = rememberCoroutineScope()
        ModalDrawerSheet {
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
                    scope.launch {
                        drawerState.apply {
                            if (isOpen) close()
                        }
                    }
                    navigator.replace(ShowGroupScreen(modifier= modifier))

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
                    scope.launch {
                        drawerState.apply {
                            if (isOpen) close()
                        }
                    }
                    navigator.replace(ReabastecimentoListScreen())

                },
                modifier = Modifier.padding(start = 16.dp, top = 6.dp)
            )
            DrawerMenuItens(listOf(cadastroItem, gastosItem))
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview
    @Composable
    fun TopBar(drawerState: DrawerState) {
        val scope = rememberCoroutineScope()
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
    }



}

