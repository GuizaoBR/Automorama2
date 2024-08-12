import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.FormatColorFill
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Moving
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.key.*
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

import helpers.WindowSize
import helpers.WindowSize.Companion.screenSizeWidth
import helpers.chekDateValidWithouScape
import helpers.filterNumbersAndDecimal
import helpers.isValidDay
import helpers.isValidMonth
import helpers.toBrazilFormatWithoutScape
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone

import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.reabastecimentoForm.ReabastecimentoFormUIState
import ui.theme.AutomoramaTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ReabastecimentoForm(
    uiState: ReabastecimentoFormUIState = ReabastecimentoFormUIState(),
    modifier: Modifier = Modifier,
    onSaveClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    var (id, combustivel, veiculo, valorTotal, valorLitro, litro, data, quilometragemAnterior, quilometragemAtual, quilometroLitro) = uiState

    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Reabastecimento",
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
                        onClick = onSaveClick,
                        enabled = uiState.isValid,
                        shape = RoundedCornerShape(50.dp)

                    ) {
                        Text("Salvar")
                    }
                },

                )


        }) { innerPadding ->
        var screenWidth by remember { mutableStateOf(0.dp) }
        var screenHeight by remember { mutableStateOf(0.dp) }
        var screenSize by remember { mutableStateOf(WindowSize.COMPACT) }
        var arragement by remember { mutableStateOf(Arrangement.Center) }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(innerPadding)
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

                ) {
                BoxWithConstraints(modifier = Modifier.padding(16.dp)) {
                    screenSize = screenSizeWidth(this.maxWidth)


                    screenWidth = this.maxWidth
                    screenHeight = this.maxHeight
                }
                arragement = when (screenSize) {
                    WindowSize.COMPACT -> Arrangement.Center
                    WindowSize.MEDIUM -> Arrangement.SpaceAround
                    WindowSize.LARGE -> Arrangement.SpaceEvenly
                }
                val composable: @Composable (@Composable (Modifier) -> Unit) -> Unit = { content ->
                    when (screenSize) {
                        WindowSize.COMPACT -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                content(Modifier)
                            }
                        }

                        else -> {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = arragement
                            ) {
                                val modifiedModifier = modifier.then(Modifier.weight(1f))
                                content(modifiedModifier)
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth()
                )
                {
                    composable { modifier ->

                        TextField(
                            value = filterNumbersAndDecimal(quilometragemAnterior),
                            onValueChange = uiState.onQuilometragemAnteriorChange,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            label = {
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Icon(
                                        imageVector = Icons.Default.Moving,
                                        contentDescription = null
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))

                                    Text("Quilometragem Anterior")

                                }
                            },
                            isError = quilometragemAnterior.isEmpty(),
                            modifier = modifier.onKeyEvent {
                                if (it.key == Key.Enter || it.key == Key.Tab || it.key == Key.NumPadEnter) {
                                    focusManager.moveFocus(FocusDirection.Next)
                                    true
                                } else {
                                    false
                                }
                            }
                        )


                        if (screenSize == WindowSize.LARGE || screenSize == WindowSize.MEDIUM) {
                            Spacer(modifier = Modifier.width(8.dp))
                        } else {
                            Spacer(modifier = Modifier.height(16.dp))
                        }


                        TextField(
                            value = filterNumbersAndDecimal(quilometragemAtual),
                            onValueChange = uiState.onQuilometragemAtualChange,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            label = {
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = null
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Quilometragem Final")
                                }


                            },
                            isError = quilometragemAtual.isEmpty(),
                            modifier = modifier.onKeyEvent {
                                if (it.key == Key.Enter || it.key == Key.Tab || it.key == Key.NumPadEnter) {
                                    focusManager.moveFocus(FocusDirection.Next)
                                    true
                                } else {
                                    false
                                }
                            }
                        )

                    }

                    Spacer(modifier = Modifier.height(16.dp))


                    composable { modifier ->

                        TextField(
                            value = filterNumbersAndDecimal(litro),
                            onValueChange = uiState.onLitroChange,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            label = {
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Icon(
                                        imageVector = Icons.Default.FormatColorFill,
                                        contentDescription = null
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Litros")
                                }


                            },
                            isError = litro.isEmpty(),
                            modifier = modifier.onKeyEvent {
                                if (it.key == Key.Enter || it.key == Key.Tab || it.key == Key.NumPadEnter ) {
                                    focusManager.moveFocus(FocusDirection.Next)
                                    true
                                } else {
                                    false
                                }
                            }
                        )


                        if (screenSize == WindowSize.LARGE || screenSize == WindowSize.MEDIUM) {
                            Spacer(modifier = Modifier.width(8.dp))
                        } else {
                            Spacer(modifier = Modifier.height(16.dp))
                        }


                        TextField(
                            value = filterNumbersAndDecimal(valorTotal),
                            onValueChange = uiState.onValorTotalChange,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            label = {

                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Icon(
                                        imageVector = Icons.Default.Payment,
                                        contentDescription = null
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Valor Total")
                                }

                            },
                            isError = valorTotal.isEmpty(),
                            modifier = modifier.onKeyEvent {
                                if (it.key == Key.Enter || it.key == Key.Tab || it.key == Key.NumPadEnter) {
                                    focusManager.moveFocus(FocusDirection.Next)
                                    true
                                } else {
                                    false
                                }
                            }
                        )

                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    composable { modifier ->

                        TextField(
                            value = filterNumbersAndDecimal(valorLitro),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            onValueChange = uiState.onValorLitroChange,
                            label = {
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Icon(
                                        imageVector = Icons.Default.Payments,
                                        contentDescription = null
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Valor Litro")
                                }

                            },
                            isError = valorLitro.isEmpty(),
                            modifier = modifier.onKeyEvent {
                                if (it.key == Key.Enter || it.key == Key.Tab || it.key == Key.NumPadEnter) {
                                    focusManager.moveFocus(FocusDirection.Next)
                                    true
                                } else {
                                    false
                                }
                            }
                        )


                        if (screenSize == WindowSize.LARGE || screenSize == WindowSize.MEDIUM) {
                            Spacer(modifier = Modifier.width(8.dp))
                        } else {
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        var openDialog by remember { mutableStateOf(false) }
                        val dateMask = "__/__/____"
                        val BrazilDateFormatOffSet = object :OffsetMapping {
                            override fun originalToTransformed(offset: Int): Int {
                                return when {
                                    offset <= 1 -> offset
                                    offset <= 3 -> offset + 1
                                    offset <= 8 -> offset + 2
                                    else -> offset
                                }
                            }

                            override fun transformedToOriginal(offset: Int): Int {
                                return when {
                                    offset <= 2 -> offset
                                    offset <= 5 -> offset - 1
                                    offset <= 10 -> offset - 2
                                    else -> 8
                                }
                            }
                        }
                        TextField(
                            value = data,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            onValueChange = { newValue ->
                                val filteredValue = newValue.filter { it.isDigit() }.take(8)
                                if(filteredValue.length == 1) {
                                    if (filteredValue.toInt() > 3)
                                        return@TextField
                                }

                                val day = if (filteredValue.length >= 2) filteredValue.substring(0, 2) else ""
                                if(filteredValue.length == 3){
                                    if(filteredValue.substring(2,3).toInt() > 1)
                                        return@TextField
                                }
                                val month = if (filteredValue.length >= 4) filteredValue.substring(2, 4) else ""
                                if (
                                    (day.isEmpty() || (isValidDay(day) && day.length == 2)) &&
                                    (month.isEmpty() || (isValidMonth(month) && month.length == 2))
                                ) {
                                    uiState.onDataChange(filteredValue)
                                } else {
                                    // Optionally: provide feedback to the user about invalid input
                                }

                            },
                            visualTransformation = VisualTransformation { text ->

                                val maskedText = StringBuilder()
                                var textIndex = 0
                                for (i in dateMask.indices) {
                                    if (textIndex < text.length) { // Use text.length here
                                        maskedText.append(text[textIndex++]) // Use text[textIndex] here
                                        if (i == 1 || i == 3) {
                                            maskedText.append('/')

                                        }
                                    } else {
                                        break
                                    }
                                }
                                TransformedText(
                                    text = AnnotatedString(maskedText.toString()),
                                    offsetMapping = BrazilDateFormatOffSet
                                )
                            },
                            trailingIcon = {
                                IconButton(
                                    onClick = { openDialog = true },

                                    ){
                                    Icon(
                                        imageVector = Icons.Default.CalendarMonth,
                                        contentDescription = null,
                                        modifier = Modifier.clickable { openDialog = true }
                                    )
                                }

                            },
                            label = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CalendarMonth,
                                        contentDescription = null
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Data")


                                }
                            },
                            isError = data.isEmpty() || !data.chekDateValidWithouScape() ,
                            modifier = modifier.onKeyEvent {
                                if (it.key == Key.Enter || it.key == Key.Tab || it.key == Key.NumPadEnter) {

                                    focusManager.moveFocus(FocusDirection.Next)
                                    true
                                } else {
                                    false
                                }
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                disabledTextColor = LocalContentColor.current.copy(
                                    LocalContentAlpha.current
                                ), // Cor do texto desabilitado igual ao habilitado
                                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant, // Cor da label desabilitada
                                disabledIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant, // Cor do indicador desabilitado
                                focusedIndicatorColor = MaterialTheme.colorScheme.primary, // Cor do indicador focado (igual ao habilitado)
                                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant // Cor do indicador não focado
                            )
                        )

                        if (openDialog) {
                            val datePickerState = rememberDatePickerState()
                            val confirmEnabled = remember {
                                derivedStateOf { datePickerState.selectedDateMillis != null }
                            }
                            DatePickerDialog(
                                onDismissRequest = {
                                    openDialog = false
                                },
                                confirmButton = {
                                    TextButton(
                                        onClick = {
                                            openDialog = false
                                            data = Instant.fromEpochMilliseconds(datePickerState.selectedDateMillis!!)
                                                .toLocalDateTime(TimeZone.UTC).date.toBrazilFormatWithoutScape()
                                            uiState.onDataChange(data)

                                        },
                                        enabled = confirmEnabled.value
                                    ) {
                                        Text("OK")
                                    }
                                },
                                dismissButton = {
                                    TextButton(onClick = {
                                        openDialog = false
                                    }) { Text("Cancel") }
                                }
                            ) {
                                DatePicker(state = datePickerState)
                            }
                        }

                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    composable { modifier ->

                        var expanded by remember { mutableStateOf(false) }

                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded }
                        ) {
                            TextField(
                                value = uiState.combustivel?.nome?: "Selecione",
                                onValueChange = {
//                                            uiState.onCombustivelChange(combustivel!!)

                                },
                                readOnly = true,
                                label = { Text("Combustível") },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = expanded
                                    )
                                },
                                modifier = modifier.menuAnchor()
                            )

                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                uiState.combustiveis.forEach { combustivel ->
                                    DropdownMenuItem(
                                        text = { Text(combustivel.nome) },
                                        onClick = {
                                            uiState.onCombustivelChange(combustivel)
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }


                    }

                }
            }
        }
    }


}

@Preview
@Composable
fun ReabastecimentoFormPreview() {
    AutomoramaTheme {
        ReabastecimentoForm()
    }
}