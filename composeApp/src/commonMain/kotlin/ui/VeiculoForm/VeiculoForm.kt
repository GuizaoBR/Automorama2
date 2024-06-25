package ui.VeiculoForm

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.DEFAULT_CONCURRENCY

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VeiculoForm(uiState: VeiculoFormUIState,
                modifier: Modifier = Modifier,
                onSaveClick: () -> Unit,
                onBackClick: () -> Unit = {},
                ){
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection).then(Modifier.padding(bottom= 0.dp)),
        topBar = {
            TopAppBar(
                title = {
                    Text(uiState.topAppBarTitle,
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
                    titleContentColor = MaterialTheme.colorScheme.primary),
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
            val(fabricante, modelo, anoFabricacao, anoModelo, placa, apelido) = uiState

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.Top),

                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                OutlinedTextField(
                    value = fabricante,
                    onValueChange = uiState.onFabricanteChange ,
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
                    isError =  uiState.modelo.isEmpty()
                )
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = anoFabricacao.toString(),
                        onValueChange = uiState.onAnoFabricacaoChange,
                        label = {
                            Text("Ano Fabricação")
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        isError = uiState.anoFabricacao.isEmpty()
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = anoModelo.toString(),
                        onValueChange = uiState.onAnoModeloChange,
                        label = {
                            Text("Ano Modelo")
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        isError = uiState.anoModelo.isEmpty()
                    )

                }
                OutlinedTextField(
                    value = placa,
                    onValueChange = uiState.onPlacaChange,
                    label = {
                        Text("Placa")
                    },
                    isError = uiState.placa.isEmpty()

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
    )



}


