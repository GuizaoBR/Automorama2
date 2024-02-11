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
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import data.models.Veiculo

@Composable
fun VeiculoForm(uiState: VeiculoFormUIState,
                modifier: Modifier = Modifier,
                onSaveClick: () -> Unit
                ){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(uiState.topAppBarTitle,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
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
                    .padding(16.dp)
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
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.SpaceBetween,
//        modifier = Modifier
//            .padding(16.dp)
//            .fillMaxSize()
//    ) {
//        OutlinedTextField(
//            value = veiculo.marca,
//            onValueChange = { veiculo.marca = it },
//            label = {
//                Text("Fabricante")
//            }
//        )
//        OutlinedTextField(
//            value = veiculo.modelo,
//            onValueChange = { veiculo.modelo = it },
//            label = {
//                Text("Modelo")
//            }
//        )
//        Row(modifier = Modifier.fillMaxWidth()) {
//            OutlinedTextField(
//                value = veiculo.anoFabricacao.toString(),
//                onValueChange = { veiculo.anoFabricacao = it.toLong()},
//                label = {
//                    Text("Ano Fabricação")
//                },
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                modifier = Modifier.weight(1f)
//            )
//            Spacer(modifier = Modifier.width(8.dp))
//            OutlinedTextField(
//                value = veiculo.anoModelo.toString(),
//                onValueChange = { veiculo.anoModelo = it.toLong()},
//                label = {
//                    Text("Ano Modelo")
//                },
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                modifier = Modifier.weight(1f)
//            )
//
//        }
//        OutlinedTextField(
//            value = veiculo.placa,
//            onValueChange = { veiculo.placa = it},
//            label = {
//                Text("Placa")
//            },
//
//        )
//        OutlinedTextField(
//            value = veiculo.apelido,
//            onValueChange = { veiculo.apelido = it},
//            label = {
//                Text("Apelido")
//            },
//
//            )
//        OutlinedButton(
//            onClick = {
//
//            },
//            modifier = Modifier
//                .fillMaxWidth(),
//            shape = RoundedCornerShape(50.dp)
//
//
//        ) {
//            Text("Salvar")
//        }
//
//    }


}


