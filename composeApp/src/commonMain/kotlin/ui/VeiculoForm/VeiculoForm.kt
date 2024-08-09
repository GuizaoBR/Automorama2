package ui.veiculoForm

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import helpers.WindowSize
import helpers.WindowSize.Companion.screenSizeWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.veiculoForm.VeiculoFormUIState
import ui.theme.AutomoramaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VeiculoForm(
    uiState: VeiculoFormUIState,
    modifier: Modifier = Modifier,
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit = {},
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        uiState.topAppBarTitle,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    OutlinedButton(
                        onClick = {
                            onSaveClick()
                        },
                        enabled = uiState.isValid,
                        shape = RoundedCornerShape(50.dp)

                    ) {
                        Text("Salvar")
                    }
                },

                )


        },
        content = { innerPadding ->
            CardContent(uiState, modifier.padding(innerPadding))

        }
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CardContent(
    uiState: VeiculoFormUIState = VeiculoFormUIState(),
    modifier: Modifier = Modifier,
) {
    val (fabricante, modelo, anoFabricacao, anoModelo, placa, apelido) = uiState
    var screenWidth by remember { mutableStateOf(0.dp) }
    var screenHeight by remember { mutableStateOf(0.dp) }
    var screenSize by remember { mutableStateOf(WindowSize.COMPACT) }
    var arragement by remember { mutableStateOf(Arrangement.Center) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
    ) {

        ElevatedCard(
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .fillMaxSize(fraction = 0.95f),

            )
        {
            BoxWithConstraints(modifier = Modifier.padding(16.dp)) {
                screenSize = screenSizeWidth(this.maxWidth)
                screenWidth = this.maxWidth
                screenHeight = this.maxHeight
            }
            arragement = when (screenSize) {
                WindowSize.COMPACT -> Arrangement.SpaceEvenly
                WindowSize.MEDIUM -> Arrangement.Center
                WindowSize.LARGE -> Arrangement.Center
            }
            val composable: @Composable (@Composable (Modifier) -> Unit) -> Unit = { content ->
                when (screenSize) {
                    WindowSize.COMPACT -> {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
                        ) {
                            content(Modifier)
                        }
                    }

                    else -> {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = arragement,

                            ) {
                            val modifiedModifier = Modifier.weight(1f)
                            content(modifiedModifier)
                        }
                    }
                }
            }


            LazyColumn(

            )
            {
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.Top),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {

                        OutlinedTextField(
                            value = fabricante,
                            onValueChange = uiState.onFabricanteChange,
                            label = {
                                Text("Fabricante")
                            },
                            isError = fabricante.isEmpty()
                        )
                        OutlinedTextField(
                            value = modelo,
                            onValueChange = uiState.onModeloChange,
                            label = {
                                Text("Modelo")
                            },
                            isError = uiState.modelo.isEmpty()
                        )
                        composable {
                            OutlinedTextField(
                                value = anoFabricacao,
                                onValueChange = uiState.onAnoFabricacaoChange,
                                label = {
                                    Text("Ano Fabricação")
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                isError = uiState.anoFabricacao.isEmpty()
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            OutlinedTextField(
                                value = anoModelo,
                                onValueChange = uiState.onAnoModeloChange,
                                label = {
                                    Text("Ano Modelo")
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                isError = uiState.anoModelo.isEmpty()
                            )

                        }
                        OutlinedTextField(
                            value = placa,
                            onValueChange = uiState.onPlacaChange,
                            label = {
                                Text("Placa")
                            },
                            isError = uiState.placa.isEmpty() || !uiState.isPlateValid,
                            supportingText = {
                                if (!uiState.isPlateValid) {
                                    Text(
                                        text = "Já existe um veículo com essa placa",
                                        color = MaterialTheme.colorScheme.error,
                                        fontSize = 12.sp
                                    )

                                }
                            }

                        )
                        OutlinedTextField(
                            value = apelido,
                            onValueChange = uiState.onApelidoChange,
                            label = {
                                Text("Apelido")
                            },
                        )
                    }
                }

            }
        }
    }
}

@Preview
@Composable
fun CardContetrPreview() {
    AutomoramaTheme(true) {
        CardContent()
    }
}

