package ViewModels

import app.cash.sqldelight.db.SqlDriver
import data.models.Veiculo
import data.repositories.VeiculoRepository
import data.setVeiculo
import germano.guilherme.automorama2.Automorama2Database
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import ui.VeiculoForm.VeiculoFormUIState

class VeiculoFormViewModel(
    private val repository: VeiculoRepository,
    private val id: Long?
): ViewModel() {
    private  val _uiState: MutableStateFlow<VeiculoFormUIState> = MutableStateFlow(VeiculoFormUIState())

    val uiState = _uiState.asStateFlow()
    init {
        _uiState.update {
            it.copy(topAppBarTitle = "Novo Veículo")
        }
        _uiState.update {currentState ->
            currentState.copy(
                onFabricanteChange = { fabricante ->
                    _uiState.update {
                        it.copy(fabricante= fabricante)

                    }
                    _uiState.update {
                        it.copy(isValid = checkIsValid())
                    }

                },
                onModeloChange = { modelo ->
                    _uiState.update {
                        it.copy(modelo = modelo)
                    }
                    _uiState.update {
                        it.copy(isValid = checkIsValid())
                    }

                },
                onAnoFabricacaoChange = { anoFabricacao ->
                    _uiState.update {
                        it.copy(anoFabricacao = anoFabricacao)
                    }
                    _uiState.update {
                        it.copy(isValid = checkIsValid())
                    }
                },
                onAnoModeloChange = { anoModelo ->
                    _uiState.update {
                        it.copy(anoModelo= anoModelo)
                    }
                    _uiState.update {
                        it.copy(isValid = checkIsValid())
                    }
                },
                onPlacaChange = { placa ->
                    _uiState.update {
                        it.copy(placa= placa)
                    }
                    _uiState.update {
                        it.copy(isValid = checkIsValid())
                    }
                },
                onApelidoChange = { apelido ->
                    _uiState.update {
                        it.copy(apelido= apelido)
                    }
                    _uiState.update {
                        it.copy(isValid = checkIsValid())
                    }

                },


            )
        }
        id?.let {
            MainScope().launch {
                val veiculo = repository.veiculos.value
                    .find {
                        it.id == id
                    }
                if (veiculo != null) {
                    _uiState.update {
                        it.copy(
                            topAppBarTitle = "Editando Veículo",
                            fabricante = veiculo.fabricante,
                            modelo = veiculo.modelo,
                            anoFabricacao =  veiculo.anoFabricacao.toString(),
                            anoModelo = veiculo.anoModelo.toString(),
                            placa = veiculo.placa,
                            apelido = veiculo.apelido
                        )
                    }
                }
            }


            }
        }




    fun checkIsValid(): Boolean {
        return with(_uiState.value){
            this.fabricante.isNotEmpty() && this.modelo.isNotEmpty() && this.anoModelo.isNotEmpty() && this.anoFabricacao.isNotEmpty() && this.placa.isNotEmpty()
        }
    }

    fun save() {
        with(_uiState.value) {
            repository.saveVeiculo(
                Veiculo(
                    id = id,
                    fabricante = this.fabricante,
                    modelo = this.modelo,
                    anoFabricacao = this.anoFabricacao.toLong(),
                    anoModelo  = this.anoModelo.toLong(),
                    placa = this.placa,
                    apelido = this.apelido
                )
            )
        }

    }
}