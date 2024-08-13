package ui.veiculoForm

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import helpers.WindowSize
import helpers.WindowSize.Companion.screenSizeWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import helpers.filterNumbers
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.veiculoForm.VeiculoFormUIState
import ui.theme.AutomoramaTheme
import ui.topAppBar.TopAppBarSave

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
            TopAppBarSave(title = uiState.topAppBarTitle,
                onBack = onBackClick,
                onSave = onSaveClick,
                isValid = uiState.isValid,)
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

            val focusManager = LocalFocusManager.current
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.Top),
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
            )
            {

                OutlinedTextField(
                    value = fabricante,
                    onValueChange = uiState.onFabricanteChange,
                    label = {
                        Text("Fabricante")
                    },
                    isError = fabricante.isEmpty(),
                    modifier = Modifier.onKeyEvent {
                        if (it.key == Key.Enter || it.key == Key.Tab || it.key == Key.NumPadEnter) {
                            focusManager.moveFocus(FocusDirection.Next)
                            true
                        } else {
                            false
                        }
                    }
                )
                OutlinedTextField(
                    value = modelo,
                    onValueChange = uiState.onModeloChange,
                    label = {
                        Text("Modelo")
                    },
                    isError = uiState.modelo.isEmpty(),
                    modifier = Modifier.onKeyEvent {
                        if (it.key == Key.Enter || it.key == Key.Tab || it.key == Key.NumPadEnter) {
                            focusManager.moveFocus(FocusDirection.Next)
                            true
                        } else {
                            false
                        }
                    }
                )
                composable {
                    OutlinedTextField(
                        value = filterNumbers(anoFabricacao),
                        onValueChange = uiState.onAnoFabricacaoChange,
                        label = {
                            Text("Ano Fabricação")
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = uiState.anoFabricacao.isEmpty(),
                        modifier = Modifier.onKeyEvent {
                            if (it.key == Key.Enter || it.key == Key.Tab || it.key == Key.NumPadEnter) {
                                focusManager.moveFocus(FocusDirection.Next)
                                true
                            } else {
                                false
                            }
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = filterNumbers(anoModelo),
                        onValueChange = uiState.onAnoModeloChange,
                        label = {
                            Text("Ano Modelo")
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = uiState.anoModelo.isEmpty(),
                        modifier = Modifier.onKeyEvent {
                            if (it.key == Key.Enter || it.key == Key.Tab || it.key == Key.NumPadEnter) {
                                focusManager.moveFocus(FocusDirection.Next)
                                true
                            } else {
                                false
                            }
                        }
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
                    },
                    modifier = Modifier.onKeyEvent {
                        if (it.key == Key.Enter || it.key == Key.Tab || it.key == Key.NumPadEnter) {
                            focusManager.moveFocus(FocusDirection.Next)
                            true
                        } else {
                            false
                        }
                    }

                )
                OutlinedTextField(
                    value = apelido,
                    onValueChange = uiState.onApelidoChange,
                    label = {
                        Text("Apelido")
                    },
                    modifier = Modifier.onKeyEvent {
                        if (it.key == Key.Enter || it.key == Key.Tab || it.key == Key.NumPadEnter) {
                            focusManager.moveFocus(FocusDirection.Next)
                            true
                        } else {
                            false
                        }
                    }
                )
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

