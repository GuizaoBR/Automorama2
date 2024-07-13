package viewModels

import cafe.adriel.voyager.core.model.ScreenModel
import com.hoc081098.kmp.viewmodel.ViewModel
import data.models.Veiculo
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repositoryFactory.VeiculoRepositoryFactory
import ui.VeiculoForm.VeiculoFormUIState

class VeiculoFormViewModel(
    private val id: Long?
): ScreenModel, KoinComponent {
    private  val _uiState: MutableStateFlow<VeiculoFormUIState> = MutableStateFlow(VeiculoFormUIState())
    private val veiculoRepositoryFactory: VeiculoRepositoryFactory by inject()
    private val repository = veiculoRepositoryFactory.create()

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