package ui.Veiculos

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
) {
    Box(modifier) {
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
        LazyColumn(Modifier.fillMaxSize()) {
            items(uiState.veiculos) { veiculo ->
                Card(
                    elevation = 4.dp,
                    modifier = Modifier
                        .padding(16.dp)

                        .combinedClickable(
                            onClick = {
                                onVeiculoClick(veiculo)
                            },
                            onLongClick = {
                            }
                        )

                ) {
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {

                        FlowRow(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            FlowColumn {
                                Text(
                                    text = veiculo.fabricante,
                                    style = TextStyle.Default.copy(
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            FlowColumn {
                                Text(
                                    text = veiculo.modelo,
                                    style = TextStyle.Default.copy(
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
//                            Spacer(modifier = Modifier.fillMaxWidth())

                        }
                        FlowRow(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            FlowColumn {
                                Text(
                                    text = "${veiculo.anoFabricacao}/${veiculo.anoModelo}",
                                    style = TextStyle.Default.copy(
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 20.sp
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            FlowColumn {
                                Text(
                                    text = veiculo.placa,
                                    style = TextStyle.Default.copy(
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 20.sp
                                    )
                                )
                            }
                        }
                    }


                }
            }
        }
    }
}
