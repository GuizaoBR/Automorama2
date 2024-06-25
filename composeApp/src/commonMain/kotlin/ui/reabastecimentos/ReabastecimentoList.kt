package ui.reabastecimentos

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingFlat
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import data.models.Reabastecimento
import data.models.Veiculo
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.recursos.reabastecimentos.screens.ReabastecimentoFormScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VeiculoDropDownTopAppBar(veiculos: List<Veiculo>, onVeiculoSelected: (Veiculo) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedVehicle by remember { mutableStateOf<Veiculo?>(null) }

    TopAppBar(
        title = { Text(selectedVehicle?.modelo ?: "Selecione um veículo") },
        actions = {
            Box {
                TextButton(
                    onClick = { expanded = true },
                    content = { Text(selectedVehicle?.modelo ?: "Selecione") }
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    veiculos.forEach { vehicle ->
                        DropdownMenuItem(onClick = {
                            selectedVehicle = vehicle
                            onVeiculoSelected(vehicle)
                            expanded = false
                        }, text = { Text("${vehicle.fabricante} ${vehicle.modelo}")})
                    }
                }
            }
        }
    )
}
@Composable
@Preview
fun ReabastecimentoListPreview() {
    ReabastecimentoList()
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ReabastecimentoList(modifier: Modifier = Modifier, newReabastecimento: () -> Unit = {}) {

    val mock = Reabastecimento().createReabastecimentosList()
    val mockVeiculos = listOf(
    Veiculo(id = 1, fabricante = "Toyota", modelo = "Corolla", anoFabricacao = 2016, anoModelo = 2016, placa = "XYZ123", apelido = "Meu carro"),
    Veiculo(id = 2, fabricante = "Honda", modelo = "Civic", anoFabricacao = 2018, anoModelo = 2018, placa = "ABC456", apelido = "Carro da esposa"),
    Veiculo(id = 3, fabricante = "Ford", modelo = "Mustang", anoFabricacao = 2022, anoModelo = 2022, placa = "DEF789", apelido = "Carro do sonho")
)
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { VeiculoDropDownTopAppBar(mockVeiculos, { }) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { newReabastecimento() },
                text = { Text("Novo Reabastecimento") },
                icon = { Icon(Icons.Filled.Add, contentDescription = "Adicione novo reabastecimento") },
            )
        },
    ) {
        Box(Modifier.fillMaxSize()) {
            ExtendedFloatingActionButton(
                onClick = { },
                content = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "Adicione novo reabastecimento")
                        Text(text = "Novo Reabastecimento")
                    }
                },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomEnd)
                    .zIndex(1f)
            )

            LazyVerticalGrid(columns = GridCells.Adaptive(300.dp), contentPadding = it) {
                items(mock) { reabastecimento ->

                    Card(Modifier, reabastecimento)


                }
            }

        }
    }
}


@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
@Preview
fun Card(
    modifier: Modifier = Modifier,
    reabastecimento: Reabastecimento
) {
    var expandedState by remember { mutableStateOf(false) }
    ElevatedCard(
        modifier = Modifier
            .height(if (expandedState) 205.dp else 180.dp)
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .combinedClickable(
                onClick = {

                },
            )
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )

    ) {
        CardContent(Modifier, reabastecimento, expandedState, { expandedState = !expandedState })
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
@Preview
fun CardContent(
    modifier: Modifier = Modifier, reabastecimento: Reabastecimento, expandedState: Boolean,
    onClick: () -> Unit
) {

    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )
    var showDeleteDialog by remember { mutableStateOf(false) }


    Box(modifier) {
        FlowColumn(
            modifier = Modifier
                .align(Alignment.BottomEnd)
        )
        {
            IconButton(
                modifier = Modifier
                    .alpha(0.2f)
                    .rotate(rotationState),
                onClick = onClick
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Drop-Down Arrow"
                )
            }
        }
        FlowColumn {
            Icon(
                imageVector = Icons.Default.LocalGasStation,
                contentDescription = "Local Gas Stattion",
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 8.dp)
                    .size(40.dp)

            )

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
            ) {
                Text(
                    text = reabastecimento.data.format(LocalDate.Format {
                        dayOfMonth()
                        char('/')
                        monthNumber()
                        char('/')
                        year()
                    }),
                    style = TextStyle.Default.copy(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 50.dp),
                    textAlign = TextAlign.End
                )
                Spacer(modifier = Modifier.padding(start = 8.dp))
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                ) {
                    Box(Modifier.align(Alignment.CenterVertically)) {
                        Text(
                            text = reabastecimento.combustivel.nome,
                            style = TextStyle.Default.copy(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            textAlign = TextAlign.End
                        )
                    }
                    Spacer(modifier = Modifier.padding(start = 8.dp))
                    Box(Modifier.align(Alignment.CenterVertically)) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.TrendingFlat,
                            contentDescription = null
                        )
                    }
                    Spacer(modifier = Modifier.padding(start = 8.dp))
                    Box(Modifier.align(Alignment.CenterVertically)) {
                        Text(
                            text = "${reabastecimento.quilometroLitro} km/l",
                            style = TextStyle.Default.copy(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
                FlowRow(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp)
                        .fillMaxWidth(),
                ) {
                    Box(Modifier.align(Alignment.CenterVertically)) {
                        Text(
                            text = "R$ ${reabastecimento.valorTotal}",
                            style = TextStyle.Default.copy(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                    Box(Modifier.align(Alignment.CenterVertically)) {

                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.TrendingFlat,
                            contentDescription = null
                        )
                    }
                    Box(Modifier.align(Alignment.CenterVertically)) {

                        Text(
                            text = "R$ ${reabastecimento.valorLitro} /l",
                            style = TextStyle.Default.copy(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }


            }
            FlowRow(
                modifier = Modifier.padding(start = 16.dp, top = 8.dp)
            ) {
                Box(Modifier.align(Alignment.CenterVertically)) {
                    Icon(
                        imageVector = Icons.Default.Moving,
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.padding(start = 8.dp))
                Box(Modifier.align(Alignment.CenterVertically)) {
                    Text(
                        text = "${reabastecimento.quilometragemAtual - reabastecimento.quilometragemAnterior} Km"
                    )
                }
            }
            if (expandedState) {
                FlowRow(
                    modifier = Modifier
                        .padding(end = 60.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center

                ) {
                    OutlinedButton(
                        onClick = {
                            showDeleteDialog = true
                        },
                        shape = RoundedCornerShape(50.dp)

                    ) {
                        Text("Deletar")
                    }
                }
            }

        }
    }


}
//    if(showDeleteDialog){
//        AlertDeleteVeiculo(reabastecimento, onDeleteClick) { showDeleteDialog = false }
//    }


//@OptIn(ExperimentalLayoutApi::class)
//@Composable
//fun AlertDeleteVeiculo(veiculo: Veiculo = Veiculo(), onDeleteClick: (Veiculo) -> Unit = {}, onCancelClick: () -> Unit = {}){
//    AlertDialog(
//        title = {
//            Text(text = "ATENÇÃO")
//        },
//        text = {
//            Column {
//                Row(Modifier.fillMaxWidth()
//                ) {
//                    Text(text = "Deseja excluir esse veículo?")
//                }
//                Spacer(Modifier.size(16.dp))
//                if (veiculo.apelido.isEmpty()){
//
//                    FlowColumn {
//                        FlowRow(
//                            modifier = Modifier
//                                .fillMaxWidth(),
//                            horizontalArrangement = Arrangement.Center
//                        ) {
//                            Text(
//                                text = veiculo.fabricante,
//                                style = TextStyle.Default.copy(
//                                    fontSize = 15.sp,
//                                    fontWeight = FontWeight.Bold
//                                )
//                            )
//                            Spacer(modifier = Modifier.size(16.dp))
//                            Text(
//                                text = veiculo.modelo,
//                                style = TextStyle.Default.copy(
//                                    fontSize = 15.sp,
//                                    fontWeight = FontWeight.Bold
//                                )
//                            )
//                        }
//                        FlowRow(
//                            modifier = Modifier
//                                .padding(16.dp)
//                                .fillMaxWidth(),
//                            horizontalArrangement = Arrangement.Center
//                        ) {
//                            Text(
//                                text = "${veiculo.anoFabricacao}/${veiculo.anoModelo}",
//                                style = TextStyle.Default.copy(
//                                    fontWeight = FontWeight.Medium,
//                                    fontSize = 15.sp
//                                )
//                            )
//                            Spacer(modifier = Modifier.width(16.dp))
//                            Text(
//                                text = veiculo.placa,
//                                style = TextStyle.Default.copy(
//                                    fontWeight = FontWeight.Medium,
//                                    fontSize = 15.sp
//                                )
//                            )
//
//                        }
//                    }
//                } else {
//                    FlowColumn {
//                        FlowRow(
//                            modifier = Modifier
//                                .fillMaxWidth(),
//                            horizontalArrangement = Arrangement.Center
//                        ) {
//                            Text(
//                                text = veiculo.apelido,
//                                style = TextStyle.Default.copy(
//                                    fontSize = 15.sp,
//                                    fontWeight = FontWeight.Bold
//                                )
//                            )
//                        }
//                    }
//                }
//            }
//               },
//        onDismissRequest = {
//
//        },
//        confirmButton = {
//            TextButton(
//                onClick = {
//                    onDeleteClick(veiculo)
//                    onCancelClick()
//                }
//            ) {
//                Text("Sim")
//            }
//                        },
//        dismissButton = {
//            TextButton(
//                onClick = onCancelClick
//            ) {
//                Text("Não")
//            }
//        }
//    )
//}





