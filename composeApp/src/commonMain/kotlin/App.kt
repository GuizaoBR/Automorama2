import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.transitions.SlideTransition
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.cadastro.NavigationBar
import ui.cadastro.screens.VeiculoListScreen
import ui.drawerMenu.DrawerMenuItens

class App() : KoinComponent {


    val resolution: Pair<Int, Int> by inject()
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun App() {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        MaterialTheme {
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet {
                        DrawerMenuItens()
                    }
                }
            ){

                var showBottom by remember { mutableStateOf(true) }

                val offset by animateIntOffsetAsState(
                    targetValue = if (showBottom) {
                        IntOffset.Zero
                    } else {
                        IntOffset(convertDpToPx(resolution.second.toFloat()) * -1, 0)
                    },
                    label = "offset"
                )
                cafe.adriel.voyager.navigator.Navigator(screen = VeiculoListScreen(onFormClicked = {
                    showBottom = false
                },
                    onFormFinished = {
                        showBottom = true
                    },
                    modifier = Modifier.padding(top = 60.dp, bottom = 80.dp)
                )) { navigator ->
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
                            NavigationBar(
                                onCombustivelClick = { navigator.push(ui.cadastro.screens.CombustivelListScreen(
                                    onFormClicked = {
                                        showBottom = false
                                    },
                                    onFormFinished = {
                                        showBottom = true
                                    },
                                    modifier = Modifier.padding(top = 60.dp, bottom = 80.dp)
                                )) },
                                onVeiculoClick = { navigator.pop() },
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


}

