package ui.reabastecimentos

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.models.Reabastecimento
import data.models.Veiculo
import helpers.toStringeWithCommaFormat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.theme.AutomoramaTheme
import ui.theme.secondaryDark


@Composable
@Preview
fun VeiculosDropDownPreview(){
    AutomoramaTheme(true){
        val mockVeiculos = listOf(
            Veiculo(id = 1, fabricante = "Toyota", modelo = "Corolla", anoFabricacao = 2016, anoModelo = 2016, placa = "XYZ123", apelido = "Meu carro"),
            Veiculo(id = 2, fabricante = "Honda", modelo = "Civic", anoFabricacao = 2018, anoModelo = 2018, placa = "ABC456", apelido = "Carro da esposa"),
            Veiculo(id = 3, fabricante = "Ford", modelo = "Mustang", anoFabricacao = 2022, anoModelo = 2022, placa = "DEF789", apelido = "Carro do sonho")
        )
        VeiculoDropDownTopAppBar()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VeiculoDropDownTopAppBar(
    uiState: ReabastecimentoListUIState = ReabastecimentoListUIState()
) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ){
                Box(modifier = Modifier
                    .fillMaxWidth(fraction = 0.7f)
                ) {
                    TextButton(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .background(Color.Black.copy(alpha = 0.2f), shape = RoundedCornerShape(16.dp))
                            .fillMaxWidth(),
                        onClick = { expanded = true },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = secondaryDark
                        ),
                        content = { Text(uiState.veiculo?.ShowIdentifier() ?: "Selecione") }
                    )
                    DropdownMenu(
                        modifier = Modifier.align(Alignment.Center).fillMaxWidth(fraction = 0.7f),
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    ) {
                        uiState.veiculos.forEach { veiculo ->
                            DropdownMenuItem(
                                onClick = {
                                uiState.onChangeVeiculo(veiculo)
                                expanded = false
                            },
                                text = { Text(veiculo.ShowIdentifier())})
                        }
                    }
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Drop-Down Arrow",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .alpha(0.2f),
                )

                }
            }
                },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            titleContentColor = secondaryDark
        )

    )
}
@Composable
@Preview
fun ReabastecimentoListPreview() {
    AutomoramaTheme(true){

       ReabastecimentoList()
    }
}
@Composable
fun AnimatedFAB(uiState: ReabastecimentoListUIState, newReabastecimento: (Long) -> Unit) {
    val targetAlpha by animateFloatAsState(
        targetValue = if (uiState.veiculo != null) 1f else 0f, label = "FAB Alpha Animation"
    )

    ExtendedFloatingActionButton(
        onClick = { uiState.veiculo?.id?.let { newReabastecimento(it) } },
        text = { Text("Novo Reabastecimento") },
        icon = { Icon(Icons.Filled.Add, contentDescription = "Adicione novo reabastecimento") },
        modifier = Modifier.alpha(targetAlpha)
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class, ExperimentalFoundationApi::class)
@Preview
@Composable
fun ReabastecimentoList(
    uiState: ReabastecimentoListUIState = ReabastecimentoListUIState(),
    modifier: Modifier = Modifier,
    editReabastecimento: (veiculoId: Long, reabastecimentoId: Long) -> Unit = { veiculoId: Long, reabastecimentoId: Long -> },
    newReabastecimento: (veiculoId: Long) -> Unit = {},

    ) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { VeiculoDropDownTopAppBar(uiState) },
        floatingActionButton = { AnimatedFAB(uiState, newReabastecimento) },
    ) { padding ->

        Box(Modifier.fillMaxSize()) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(300.dp),
                contentPadding = padding,
                ) {



                items(uiState.reabastecimentos.toList(), key = { it.id!! },) { reabastecimento ->
                    AnimatedListItem(
                        reabastecimento,
                        editReabastecimento,
                        onDelete = { id ->
                            uiState.onDelete(id)
                        },
                    )
                }
            }
        }
    }
}


@Composable
fun AnimatedListItem(
    reabastecimento: Reabastecimento,
    editReabastecimento: (Long, Long) -> Unit,
    onDelete: (Long) -> Unit,
) {
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
    ) {
        Card(
            Modifier,
            reabastecimento,
            editReabastecimento
        ) {
            CoroutineScope(Dispatchers.IO).launch {
                visibleState.value = false
                delay(500) // Ajuste o tempo de atraso conforme necessário
                onDelete(reabastecimento.id!!)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
@Preview
fun Card(
    modifier: Modifier = Modifier,
    reabastecimento: Reabastecimento,
    editReabastecimento: (veiculoId: Long, reabastecimentoId: Long) -> Unit,
    onDelete: (id: Long) -> Unit
) {
    var expandedState by remember { mutableStateOf(false) }
    ElevatedCard(
        modifier = Modifier
          //  .height(if (expandedState) 205.dp else 180.dp)
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .combinedClickable(
                onClick = {
                    editReabastecimento(reabastecimento.veiculo.id!!, reabastecimento.id!!)
                },
                onLongClick = {
                    expandedState = !expandedState

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
        CardContent(Modifier, reabastecimento, expandedState, { expandedState = !expandedState }, onDelete)
    }
}
@Composable
@Preview
fun CardPreview(){
    AutomoramaTheme(true){
        Card(
            reabastecimento = Reabastecimento().createReabastecimentosList().first(),
            editReabastecimento = { veiculoId, reabastecimentoId ->  },
            onDelete = {  }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
@Preview
fun CardContent(
    modifier: Modifier = Modifier, reabastecimento: Reabastecimento, expandedState: Boolean,
    onClick: () -> Unit,
    onDelete: (id: Long) -> Unit
)
{

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
        Row(modifier = Modifier.fillMaxWidth())
        {
            Column(modifier = Modifier
                .align(Alignment.CenterVertically),
                verticalArrangement = Arrangement.Center

            )
            {
                Icon(
                    imageVector = Icons.Default.LocalGasStation,
                    contentDescription = "Local Gas Stattion",
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(40.dp)

                )
            }

            Column(modifier = Modifier) {

                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                )
                {
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
                            .padding(end = 12.dp)
                            .weight(1f),
                        textAlign = TextAlign.End
                    )


//                    Spacer(modifier = Modifier.padding(start = 8.dp))
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 50.dp)
                    ) {
                        Box() {
                            Text(
                                text = reabastecimento.combustivel.nome,
                                style = TextStyle.Default.copy(
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                textAlign = TextAlign.Start
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
                                text = "${reabastecimento.quilometragemLitro.toStringeWithCommaFormat()} km/l",
                                style = TextStyle.Default.copy(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }
                    FlowRow(
                        modifier = Modifier
                            .padding(start = 16.dp, top = 8.dp, end = 50.dp)
                            .fillMaxWidth(),
                    ) {
                        Box(Modifier.align(Alignment.CenterVertically)) {
                            Text(
                                text = "R$ ${reabastecimento.valorTotal.toStringeWithCommaFormat()}",
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
                                text = "R$ ${reabastecimento.valorLitro.toStringeWithCommaFormat()} /l",
                                style = TextStyle.Default.copy(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }


                }
                FlowRow(
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 50.dp, bottom = 8.dp)
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
                            text = "${(reabastecimento.quilometragemAtual - reabastecimento.quilometragemAnterior).toStringeWithCommaFormat()} Km"
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


    if(showDeleteDialog){
        AlertDeleteVeiculo(reabastecimento, onDelete) { showDeleteDialog = false }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AlertDeleteVeiculo(reabastecimento: Reabastecimento = Reabastecimento(), onDeleteClick: (id: Long) -> Unit = {}, onCancelClick: () -> Unit = {}){
    AlertDialog(
        title = {
            Text(text = "ATENÇÃO")
        },
        text = {
            Column {
                Row(Modifier.fillMaxWidth()
                ) {
                    Text(text = "Deseja excluir esse reabastecimento?")
                }
                Spacer(Modifier.size(16.dp))

            }
               },
        onDismissRequest = {

        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDeleteClick(reabastecimento.id!!)
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





