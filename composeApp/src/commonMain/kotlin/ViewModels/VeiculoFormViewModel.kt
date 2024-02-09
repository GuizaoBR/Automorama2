package ViewModels

import app.cash.sqldelight.db.SqlDriver
import data.models.Veiculo
import data.setVeiculo
import germano.guilherme.automorama2.Automorama2Database
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import moe.tlaster.precompose.viewmodel.ViewModel
import ui.VeiculoForm.VeiculoFormUIState

class VeiculoFormViewModel(
    driver: SqlDriver,
    private val id: Long?
): ViewModel() {
    private val database = Automorama2Database(driver)
    private  val _uiState: MutableStateFlow<VeiculoFormUIState> = MutableStateFlow(VeiculoFormUIState())

    val uiState = _uiState.asStateFlow()
    init {
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


    }

    fun checkIsValid(): Boolean {
        return with(_uiState.value){
            this.fabricante.isNotEmpty() && this.modelo.isNotEmpty() && this.anoModelo.isNotEmpty() && this.anoFabricacao.isNotEmpty() && this.placa.isNotEmpty()
        }
    }

    fun save() {
        with(_uiState.value) {
            database.setVeiculo(
                Veiculo(
                    id = null,
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