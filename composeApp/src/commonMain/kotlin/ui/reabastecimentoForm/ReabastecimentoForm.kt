import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.TrendingFlat
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import data.models.Combustivel
import data.models.Reabastecimento
import helpers.WindowSize
import helpers.WindowSize.Companion.screenSizeWidth
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.theme.AutomoramaTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ReabastecimentoForm(
    reabastecimento: Reabastecimento = Reabastecimento(),
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
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
                    IconButton(onClick = { }) {
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
                        },
                        enabled = true,
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
                LazyColumn(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    item {
                        composable { modifier ->

                            TextField(
                                value = "",
                                onValueChange = { },
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
                                modifier = modifier
                            )


                            if (screenSize == WindowSize.LARGE || screenSize == WindowSize.MEDIUM) {
                                Spacer(modifier = Modifier.width(8.dp))
                            } else {
                                Spacer(modifier = Modifier.height(16.dp))
                            }


                            TextField(
                                value = "",
                                onValueChange = { },
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
                                modifier = modifier
                            )

                        }

                        Spacer(modifier = Modifier.height(16.dp))


                        composable { modifier ->

                            TextField(
                                value = "",
                                onValueChange = { },
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
                                modifier = modifier
                            )


                            if (screenSize == WindowSize.LARGE || screenSize == WindowSize.MEDIUM) {
                                Spacer(modifier = Modifier.width(8.dp))
                            } else {
                                Spacer(modifier = Modifier.height(16.dp))
                            }


                            TextField(
                                value = "",
                                onValueChange = { },
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
                                modifier = modifier
                            )

                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        composable { modifier ->

                            TextField(
                                value = "",
                                onValueChange = { },
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
                                modifier = modifier
                            )


                            if (screenSize == WindowSize.LARGE || screenSize == WindowSize.MEDIUM) {
                                Spacer(modifier = Modifier.width(8.dp))
                            } else {
                                Spacer(modifier = Modifier.height(16.dp))
                            }

                            var openDialog by remember { mutableStateOf(false) }
                            //                        val snackState = remember { SnackbarHostState() }
                            //                        val snackScope = rememberCoroutineScope()
                            var selectedDateText by remember { mutableStateOf("") } // Armazena a data selecionada como texto

                            TextField(
                                value = selectedDateText,
                                onValueChange = { },
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
                                enabled = false,
                                modifier = modifier.then(Modifier.clickable { openDialog = true }),
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
                                        // Dismiss the dialog when the user clicks outside the dialog or on the back
                                        // button. If you want to disable that functionality, simply use an empty
                                        // onDismissRequest.
                                        openDialog = false
                                    },
                                    confirmButton = {
                                        TextButton(
                                            onClick = {
                                                openDialog = false
                                                //                                            snackScope.launch {
                                                //                                                snackState.showSnackbar(
                                                //                                                    "Selected date timestamp: ${datePickerState.selectedDateMillis}"
                                                //                                                )
                                                //                                            }
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
                        val combustiveis = listOf(
                            Combustivel(1, "Gasolina"),
                            Combustivel(2, "Etanol"),
                            Combustivel(3, "Diesel")
                        )

                        composable { modifier ->

                            var expanded by remember { mutableStateOf(false) }
                            var selectedCombustivel by remember { mutableStateOf(combustiveis[0]) }

                            ExposedDropdownMenuBox(
                                expanded = expanded,
                                onExpandedChange = { expanded = !expanded }
                            ) {
                                TextField(
                                    value = selectedCombustivel.nome,
                                    onValueChange = {},
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
                                    combustiveis.forEach { combustivel ->
                                        DropdownMenuItem(
                                            text = { Text(combustivel.nome) },
                                            onClick = {
                                                selectedCombustivel = combustivel
                                                expanded = false
                                            }
                                        )
                                    }
                                }
                            }


                        }



                        Column {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text("Widht $screenWidth")
                                Text("Height $screenHeight")

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