package ui.Veiculos

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
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

@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
fun VeiculosListScreen(
    uiState: VeiculosListUiState,
    modifier: Modifier = Modifier,
    onNewVeiculoClick: () -> Unit = {},
    onVeiculoClick: (Veiculo) -> Unit = {},
    onDeleteClick: (Veiculo) -> Unit = {}
) {


    Box(modifier.fillMaxSize()) {
        ExtendedFloatingActionButton(
            onClick = onNewVeiculoClick,
            text = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Add new task icon")
                    Text(text = "Novo Veículo")
                }
            },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
                .zIndex(1f)
        )

        LazyVerticalGrid(columns = GridCells.Adaptive(300.dp)) {
            items(uiState.veiculos) { veiculo ->

                Card(
                    elevation = 6.dp,
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
                    shape = RoundedCornerShape(10.dp),

                ) {
                    CardContent(veiculo, modifier, onDeleteClick)
                }


            }
        }

    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CardContent(veiculo: Veiculo, modifier: Modifier, onDeleteClick: (Veiculo) -> Unit) {
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )
    var showDeleteDialog by remember { mutableStateOf(false) }


    Box(modifier) {
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
        FlowColumn {

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
                Spacer(modifier = Modifier.padding(16.dp))
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
            if (expandedState) {
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
                }
            }
        }


    }
    if(showDeleteDialog){
        AlertDeleteVeiculo(veiculo, onDeleteClick) { showDeleteDialog = false }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AlertDeleteVeiculo(veiculo: Veiculo, onDeleteClick: (Veiculo) -> Unit = {}, onCancelClick: () -> Unit = {}){
    AlertDialog(
        title = {
            Text(text = "ATENÇÃO")
        },
        text = {
            Column {
                Row(Modifier.fillMaxWidth()
                ) {
                    Text(text = "Deseja excluir esse veículo?")
                }
                Spacer(Modifier.size(16.dp))
                if (veiculo.apelido.isEmpty()){

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





