import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.jetpack.ProvideNavigatorLifecycleKMPSupport
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.ScaleTransition
import ui.theme.AutomoramaTheme
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.core.component.KoinComponent
import ui.cadastro.NavigationItem
import ui.cadastro.screens.GroupCadastrosScreen
import ui.recursos.reabastecimentos.screens.ReabastecimentoListScreen
import ui.drawerMenu.DrawerMenuItens

class App() : KoinComponent {



    @OptIn(ExperimentalVoyagerApi::class)
    @Preview
    @Composable
    fun App(modifier: Modifier = Modifier) {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        AutomoramaTheme{
            ProvideNavigatorLifecycleKMPSupport {

                Navigator(
                    screen = ShowGroupScreen()
                ) { navigator ->

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
    }




    fun ShowGroupScreen(): Screen {
        return GroupCadastrosScreen()
    }

    @Composable
    @Preview
    fun DrawerMenu(navigator: Navigator? = null, drawerState: DrawerState? = null, modifier: Modifier = Modifier) {
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Add, contentDescription = "Cadastro"  )
                        Spacer(modifier = Modifier.padding(start = 32.dp))
                        Text("Cadastro",
                             fontWeight = typography.titleMedium.fontWeight,
                             fontSize = typography.titleMedium.fontSize,
                             lineHeight = typography.titleMedium.lineHeight
                        )
                    }
                        },
                screen = GroupCadastrosScreen(),

                modifier = Modifier.padding(start = 16.dp, top = 6.dp)
            )
            val gastosItem = NavigationItem(
                name = "Gastos",
                label = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Payments, contentDescription = "Gastos" )
                        Spacer(modifier = Modifier.padding(start = 32.dp))
                        Text("Gastos",
                             fontWeight = typography.titleMedium.fontWeight,
                             fontSize = typography.titleMedium.fontSize,
                             lineHeight = typography.titleMedium.lineHeight
                        )
                    }
                        },
                screen = ReabastecimentoListScreen(),
                modifier = Modifier.padding(start = 16.dp, top = 6.dp)
            )

            DrawerMenuItens(listOf(cadastroItem, gastosItem)) {
                scope.launch {
                    drawerState!!.apply {
                        if (isOpen) close()
                    }
                }
            }
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
                        contentDescription = "Menu"
                    )
                }
            },
            colors = TopAppBarColors(
                containerColor = NavigationBarDefaults.containerColor,
                titleContentColor = MaterialTheme.colorScheme.primary,
                navigationIconContentColor = MaterialTheme.colorScheme.primary,
                actionIconContentColor = MaterialTheme.colorScheme.primary,
                scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer,
            )
        )
    }



}

