package ui.Combustiveis

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
import androidx.compose.foundation.layout.fillMaxHeight
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
import data.models.Combustivel

@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
fun CombustivelListScreen(
    uiState: CombustivelListUIState,
    modifier: Modifier = Modifier,
    onNewCombustivelClick: () -> Unit = {},
    onCombustivelClick: (Combustivel) -> Unit = {},
    onDeleteClick: (Combustivel) -> Unit = {}
) {


    Box(modifier.fillMaxSize()) {
        ExtendedFloatingActionButton(
            onClick = onNewCombustivelClick,
            content = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Adicionar novo combustivel")
                    Text(text = "Novo Combustível")
                }
            },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
                .zIndex(1f)
        )

        LazyVerticalGrid(columns = GridCells.Adaptive(300.dp)) {
            items(uiState.combustiveis) { combustivel ->

                ElevatedCard(
                    modifier = Modifier
                        .padding(16.dp)
                        .combinedClickable(
                            onClick = {
                                onCombustivelClick(combustivel)
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
                    CardContent(combustivel, modifier, onDeleteClick)
                }


            }
        }

    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CardContent(combustivel: Combustivel, modifier: Modifier, onDeleteClick: (Combustivel) -> Unit) {
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
        FlowColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center
        ) {
            FlowRow(
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = combustivel.nome,
                    style = TextStyle.Default.copy(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
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
        AlertDeleteCombustivel(combustivel, onDeleteClick) { showDeleteDialog = false }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AlertDeleteCombustivel(combustivel: Combustivel, onDeleteClick: (Combustivel) -> Unit = {}, onCancelClick: () -> Unit = {}){
    AlertDialog(
        title = {
            Text(text = "ATENÇÃO")
        },
        text = {
            Column {
                Row(
                    Modifier.fillMaxWidth()
                ) {
                    Text(text = "Deseja excluir o combustivel ${combustivel.nome}?")
                }

            }
        },
        onDismissRequest = {

        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDeleteClick(combustivel)
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





