package ui.veiculos

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import data.models.Veiculo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.theme.AutomoramaTheme

@Composable
fun VeiculosListScreen(
    uiState: VeiculosListUiState,
    modifier: Modifier = Modifier,
    onNewVeiculoClick: () -> Unit = {},
    onVeiculoClick: (Veiculo) -> Unit = {},
    onEditVeiculoClick: (Veiculo) -> Unit = {},
) {


    Box(modifier.fillMaxSize()) {
        ExtendedFloatingActionButton(
            onClick = onNewVeiculoClick,
            content = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Adicione novo veículo")
                    Text(text = "Novo Veículo")
                }
            },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
                .zIndex(1f)
        )

        LazyVerticalGrid(columns = GridCells.Adaptive(300.dp)) {
            items(uiState.veiculos, key = { veiculo -> veiculo.id!! }) { veiculo ->
                val visibleState = remember { mutableStateOf(true) }
                AnimatedVisibility(
                    visible = visibleState.value, // Controla se o item está visível
                    enter = fadeIn(animationSpec = tween(3000)) + expandVertically(
                        animationSpec = tween(
                            durationMillis = 3000,
                            easing = LinearOutSlowInEasing
                        )
                    ),
                    exit = shrinkVertically(
                        animationSpec = tween(
                            durationMillis = 500,
                            easing = LinearOutSlowInEasing
                        )
                    ) + fadeOut(animationSpec = tween(300))
                ){
                    CardContent(veiculo, Modifier, {
                        CoroutineScope(Dispatchers.IO).launch {
                            visibleState.value = false
                            delay(500)
                            uiState.onDelete(veiculo)
                        }
                    }, onVeiculoClick, onEditVeiculoClick)
                }
            }
        }

    }
}

@Preview
@Composable
fun CardContentPreview() {
    AutomoramaTheme(true) {
        LazyVerticalGrid(columns = GridCells.Adaptive(300.dp)) {
            item {
                CardContent(
                    Veiculo(
                        fabricante = "Fiat",
                        modelo = "Uno",
                        anoFabricacao = 2022,
                        anoModelo = 2022,
                        placa = "AAA-1234",
                        apelido = "Meu carro"
                    ),
                    onEditVeiculoClick = {},
                    )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
fun CardContent(
    veiculo: Veiculo,
    modifier: Modifier = Modifier,
    onDeleteClick: (Veiculo) -> Unit = {},
    onVeiculoClick: (Veiculo) -> Unit = {},
    onEditVeiculoClick: (Veiculo) -> Unit
) {
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )
    var showDeleteDialog by remember { mutableStateOf(false) }
    ElevatedCard(
        modifier = Modifier
            .padding(16.dp)
            .combinedClickable(
                onClick = {
                    onVeiculoClick(veiculo)
                },
            )
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        //shape = RoundedCornerShape(50.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )

    ) {

        Box(modifier)
        {
            FlowColumn(
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {

                IconButton(
                    modifier = Modifier
                        .weight(1f)
                        .alpha(0.2f)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop-Down Arrow"
                    )
                }
            }
            FlowColumn(Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {

                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = veiculo.fabricante,
                        style = TextStyle.Default.copy(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(
                        text = veiculo.modelo,
                        style = TextStyle.Default.copy(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                FlowRow(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "${veiculo.anoFabricacao}/${veiculo.anoModelo}",
                        style = TextStyle.Default.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp
                        )
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = veiculo.placa,
                        style = TextStyle.Default.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp
                        )
                    )

                }
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "${veiculo.apelido} ",
                        style = TextStyle.Default.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp
                        )
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "${veiculo.media} Km/L",
                        style = TextStyle.Default.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp
                        )
                    )

                }

                if (expandedState) {
                    Spacer(modifier = Modifier.size(16.dp))
                    FlowRow(
                        modifier = Modifier
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
                        Spacer(modifier = Modifier.size(8.dp))
                        OutlinedButton(
                            onClick = {
                                onEditVeiculoClick(veiculo)
                            },
                            shape = RoundedCornerShape(50.dp)

                        ) {
                            Text("Editar")
                        }
                    }
                }
            }


        }
    }

    if (showDeleteDialog) {
        AlertDeleteVeiculo(veiculo, onDeleteClick) { showDeleteDialog = false }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AlertDeleteVeiculo(
    veiculo: Veiculo = Veiculo(),
    onDeleteClick: (Veiculo) -> Unit = {},
    onCancelClick: () -> Unit = {}
) {
    AlertDialog(
        title = {
            Text(text = "ATENÇÃO")
        },
        text = {
            Column {
                Row(
                    Modifier.fillMaxWidth()
                ) {
                    Text(text = "Deseja excluir esse veículo?")
                }
                Spacer(Modifier.size(16.dp))
                if (veiculo.apelido.isEmpty()) {

                    FlowColumn {
                        FlowRow(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = veiculo.fabricante,
                                style = TextStyle.Default.copy(
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.size(16.dp))
                            Text(
                                text = veiculo.modelo,
                                style = TextStyle.Default.copy(
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                        FlowRow(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "${veiculo.anoFabricacao}/${veiculo.anoModelo}",
                                style = TextStyle.Default.copy(
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 15.sp
                                )
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = veiculo.placa,
                                style = TextStyle.Default.copy(
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 15.sp
                                )
                            )

                        }
                    }
                } else {
                    FlowColumn {
                        FlowRow(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = veiculo.apelido,
                                style = TextStyle.Default.copy(
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }
        },
        onDismissRequest = {

        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDeleteClick(veiculo)
                    onCancelClick()
                }
            ) {
                Text("Sim")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onCancelClick
            ) {
                Text("Não")
            }
        }
    )
}





