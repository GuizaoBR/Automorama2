package ui.cadastro

import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import ui.cadastro.screens.CombustivelListScreen
import ui.cadastro.screens.VeiculoListScreen

@Composable
fun GroupCadastros(resolution: Pair<Int, Int>, modifier: Modifier){
    var showBottom by remember { mutableStateOf(true) }
    val onHideBottom = { showBottom = false }
    val onShowBottomScreen = { showBottom = true }
    Navigator(
        screen = VeiculoListScreen(onFormClicked = onHideBottom,
                                   onFormFinished = onShowBottomScreen,
            modifier = modifier),
    ) { navigator ->
        Scaffold(
        bottomBar = {
            val offset by animateIntOffsetAsState(
                targetValue = if (showBottom) {
                    IntOffset.Zero
                } else {
                    IntOffset(convertDpToPx(resolution.second.toFloat()) * -1, 0)
                },
                label = "offset"
            )
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
                            onFormClicked = onHideBottom,
                            onFormFinished = {
                                onShowBottomScreen()
                                selectedItem = it
                                             },
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
                            onFormClicked = onHideBottom,
                            onFormFinished = onShowBottomScreen,
                            modifier = modifier
                        )
                    )
                }
            )


            if (showBottom){
                BottomBar(listOf(veiculoItem, combustivelItem), selectedItem,  modifier = Modifier.offset { offset })
            }
        }) {
            SlideTransition(navigator, modifier= Modifier.padding(it))
        }
    }

}

    @Composable
    fun BottomBar(
        itens: List<NavigationItem>,
        selectedItem: NavigationItem,
        modifier: Modifier = Modifier
    ) {

        NavigationBar(
            itens = itens,
            selectedItem = selectedItem,
            modifier = modifier
                .fillMaxWidth()

        )
    }

@Composable
    fun convertDpToPx(dp: Float): Int {
        val density = LocalDensity.current.density
        return (dp * density).toInt()
    }
