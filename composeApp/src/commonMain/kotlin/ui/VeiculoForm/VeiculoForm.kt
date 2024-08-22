package ui.veiculoForm

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import helpers.WindowSize
import helpers.WindowSize.Companion.screenSizeWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import helpers.filterNumbers
import org.jetbrains.compose.ui.tooling.preview.Preview
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
            TopAppBarSave(
                title = uiState.topAppBarTitle,
                onBack = onBackClick,
                onSave = onSaveClick,
                isValid = uiState.isValid,
            )
        },
        content = { innerPadding ->
            CardContent(uiState, modifier.padding(innerPadding), onSaveClick)

        }
    )


}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
private fun CardContent(
    uiState: VeiculoFormUIState = VeiculoFormUIState(),
    modifier: Modifier = Modifier,
    onSaveClick: () -> Unit,
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
            val (modeloFocus, anoFabricacaoFocus, anoModeloFocus, placaFocus, apelidoFocus) = FocusRequester.createRefs()
            var focused by mutableStateOf(false)
            var anoFabricacaoFocused by mutableStateOf(false)
            var anoModeloFocused by mutableStateOf(false)
            var placaFocused by mutableStateOf(false)
            var apelidoFocused by mutableStateOf(false)
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
                    singleLine = true,
                    onValueChange = uiState.onFabricanteChange,
                    label = {
                        Text("Fabricante")
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = {
                        modeloFocus.requestFocus()
                    }),
                    isError = fabricante.isEmpty(),
                    modifier = Modifier
                        .focusable()
                        .onKeyEvent {
                            if (it.key == Key.Enter || it.key == Key.Tab || it.key == Key.NumPadEnter) {
                                modeloFocus.requestFocus()
                            }
                            true
                        }
                )
                OutlinedTextField(
                    value = modelo,
                    singleLine = true,
                    onValueChange = uiState.onModeloChange,
                    label = {
                        Text("Modelo")
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = {
                        anoFabricacaoFocus.requestFocus()
                    }),
                    isError = uiState.modelo.isEmpty(),
                    modifier = Modifier
                        .focusRequester(modeloFocus)
                        .onFocusChanged {
                            if (it.isFocused && !focused) {
                                focused = true
                                return@onFocusChanged
                            }
                        }
                        .onKeyEvent {
                            if ((it.key == Key.Enter || it.key == Key.Tab || it.key == Key.NumPadEnter) && !focused) {
                                anoFabricacaoFocus.requestFocus()
                            }
                            focused = false
                            true
                        }
                )
                composable {
                    OutlinedTextField(
                        value = filterNumbers(anoFabricacao),
                        singleLine = true,
                        onValueChange = uiState.onAnoFabricacaoChange,
                        label = {
                            Text("Ano Fabricação")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(onNext = {
                            anoModeloFocus.requestFocus()
                        }),
                        isError = uiState.anoFabricacao.isEmpty(),
                        modifier = Modifier
                            .focusRequester(anoFabricacaoFocus)
                            .onFocusChanged {
                                if (it.isFocused && !anoFabricacaoFocused) {
                                    anoFabricacaoFocused = true
                                    return@onFocusChanged
                                }
                            }
                            .onKeyEvent {
                                if ((it.key == Key.Enter || it.key == Key.Tab || it.key == Key.NumPadEnter) && !anoFabricacaoFocused) {
                                    anoModeloFocus.requestFocus()
                                }
                                anoFabricacaoFocused = false
                                true
                            }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = filterNumbers(anoModelo),
                        singleLine = true,
                        onValueChange = uiState.onAnoModeloChange,
                        label = {
                            Text("Ano Modelo")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(onNext = {
                            placaFocus.requestFocus()
                        }),
                        isError = uiState.anoModelo.isEmpty(),
                        modifier = Modifier
                            .focusRequester(anoModeloFocus)
                            .onFocusChanged {
                                if (it.isFocused && !focused) {
                                    anoModeloFocused = true
                                    return@onFocusChanged
                                }
                            }
                            .onKeyEvent {
                                if ((it.key == Key.Enter || it.key == Key.Tab || it.key == Key.NumPadEnter) && !anoModeloFocused) {
                                    placaFocus.requestFocus()
                                }
                                anoModeloFocused = false
                                true
                            }
                    )

                }
                OutlinedTextField(
                    value = placa,
                    singleLine = true,
                    onValueChange = uiState.onPlacaChange,
                    label = {
                        Text("Placa")
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = {
                        apelidoFocus.requestFocus()
                    }),
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
                    modifier = Modifier
                        .focusRequester(placaFocus)
                        .onFocusChanged {
                            if (it.isFocused && !placaFocused) {
                                placaFocused = true
                                return@onFocusChanged
                            }
                        }
                        .onKeyEvent {
                            if ((it.key == Key.Enter || it.key == Key.Tab || it.key == Key.NumPadEnter) && !placaFocused) {
                                apelidoFocus.requestFocus()
                            }
                            placaFocused = false
                            true
                        }

                )
                OutlinedTextField(
                    value = apelido,
                    singleLine = true,
                    onValueChange = uiState.onApelidoChange,
                    label = {
                        Text("Apelido")
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                        if (uiState.isValid) onSaveClick()
                    }),
                    isError = !uiState.isApelidoValid,
                    supportingText = {
                        if (!uiState.isApelidoValid) {
                            Text(
                                text = "Já existe um veículo com essa apelido",
                                color = MaterialTheme.colorScheme.error,
                                fontSize = 12.sp
                            )

                        }
                    },
                    modifier = Modifier
                        .focusRequester(apelidoFocus)
                        .onFocusChanged {
                            if (it.isFocused && !apelidoFocused) {
                                apelidoFocused = true
                                return@onFocusChanged
                            }
                            apelidoFocused = false
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
        CardContent(onSaveClick = {})
    }
}

