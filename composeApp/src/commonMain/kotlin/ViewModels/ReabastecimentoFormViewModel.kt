package viewModels

import com.hoc081098.kmp.viewmodel.ViewModel
import data.models.Reabastecimento
import data.models.Veiculo
import helpers.round
import helpers.toBrazilFormat
import helpers.toBrazilFormatWithoutScape
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.char
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repositoryFactory.CombustivelRepositoryFactory
import repositoryFactory.ReabastecimentoRepositoryFactory
import ui.reabastecimentoForm.ReabastecimentoFormUIState

class ReabastecimentoFormViewModel(
    private val id: Long?,
    veiculoId: Long
): ViewModel(), KoinComponent {
    private val _uiState: MutableStateFlow<ReabastecimentoFormUIState> = MutableStateFlow(ReabastecimentoFormUIState())
    private val repositoryFactory: ReabastecimentoRepositoryFactory by inject()
    private val combustivelRepositoryFactory: CombustivelRepositoryFactory by inject()

    private val repository = repositoryFactory.create()
    private val combustivelRepository = combustivelRepositoryFactory.create()

    val uiState = _uiState.asStateFlow()

    init{

        _uiState.update { it.copy(topAppBarTitle = if (id == null) "Novo Reabastecimento" else "Editando Reabastecimento") }


        _uiState.update {
            combustivelRepository.getCombustiveis()
            it.copy(combustiveis = combustivelRepository.combustiveis.value )
        }
        _uiState.update {
            it.copy(veiculoId = veiculoId)
        }

        _uiState.update { currentState ->
            currentState.copy(
                onValorTotalChange = { valorTotal ->
                    _uiState.update { it.copy(valorTotal = valorTotal, isValid = checkIsValid()) }
                    if(_uiState.value.litro.isNotEmpty() && _uiState.value.litro.toDouble() > 0 ){
                        val valorLitro = (valorTotal.toDoubleOrNull()
                            ?.div(_uiState.value.litro.toDouble()))?.round(2)
                        _uiState.update { it.copy(valorLitro = valorLitro.toString()) }
                    }
                },
                onValorLitroChange = { valorLitro ->
                    _uiState.update { it.copy(valorLitro = valorLitro, isValid = checkIsValid()) }
                },
                onLitroChange = { litro ->
                    _uiState.update { it.copy(litro = litro, isValid = checkIsValid()) }
                },
                onDataChange = { data ->
                    _uiState.update { it.copy(data = data, isValid = checkIsValid()) }
                },
                onQuilometragemAnteriorChange = { quilometragemAnterior ->
                    _uiState.update { it.copy(quilometragemAnterior = quilometragemAnterior, isValid = checkIsValid()) }
                },
                onQuilometragemAtualChange = { quilometragemAtual ->
                    _uiState.update { it.copy(quilometragemAtual = quilometragemAtual, isValid = checkIsValid()) }
                },
                onQuilometroLitroChange = { quilometroLitro ->
                    _uiState.update { it.copy(quilometroLitro = quilometroLitro, isValid = checkIsValid()) }
                },
                onCombustivelChange = { combustivel ->
                    _uiState.update { it.copy(combustivel = combustivel) }
                    _uiState.update { it.copy(isValid = checkIsValid()) }
                },

            )
        }

        if (id != null) {
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true) }
                val reabastecimento = repository.reabastecimentos.value.find {
                    it.id == id
                }
                if (reabastecimento != null) {
                    _uiState.update {
                        it.copy(
                            id = reabastecimento.id!!,
                            combustivel = reabastecimento.combustivel,
                            veiculoId = reabastecimento.veiculo.id!!,
                            valorTotal = reabastecimento.valorTotal.toString(),
                            valorLitro = reabastecimento.valorLitro.toString(),
                            litro = reabastecimento.litro.toString(),
                            data = reabastecimento.data.toBrazilFormatWithoutScape(),
                            quilometragemAnterior = reabastecimento.quilometragemAnterior.toString(),
                            quilometragemAtual = reabastecimento.quilometragemAtual.toString(),
                            quilometroLitro = reabastecimento.quilometragemLitro.toString(),
                            isLoading = false
                        )
                    }
                } else {
                    _uiState.update { it.copy(errorMessage = "Reabastecimento not found", isLoading = false) }
                }
            }
        }
    }


    fun checkIsValid(): Boolean {
        with(_uiState.value){
            return valorTotal.isNotEmpty() &&
                    valorLitro.isNotEmpty() &&
                    litro.isNotEmpty() &&
                    data.isNotEmpty() &&
                    quilometragemAnterior.isNotEmpty() &&
                    quilometragemAtual.isNotEmpty() &&
                    combustivel != null
        }
    }

    fun saveReabastecimento() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true)}

            with(_uiState.value) { // Use with(_uiState.value) here
                val formatter = LocalDate.Format {
                    dayOfMonth()
                    monthNumber()
                    year()
                }
                val reabastecimentoToSave = Reabastecimento(
                    id = id,
                    combustivel = combustivel!!,
                    veiculo = Veiculo(id = veiculoId),
                    valorTotal = valorTotal.toDoubleOrNull() ?: 0.0,
                    valorLitro = valorLitro.toDoubleOrNull() ?: 0.0,
                    litro = litro.toDoubleOrNull() ?: 0.0,
                    data = LocalDate.parse(data, formatter),
                    quilometragemAnterior = quilometragemAnterior.toDoubleOrNull() ?: 0.0,
                    quilometragemAtual = quilometragemAtual.toDoubleOrNull() ?: 0.0,
                    quilometragemLitro =  ((quilometragemAtual.toDouble() - quilometragemAnterior.toDouble()) / litro.toDouble()).round(2)
                )

                try {
                    repository.saveReabastecimento(reabastecimentoToSave)

                    // ... (Navigation/Success message logic)
                } catch (e: Exception) {
                    _uiState.update { it.copy(errorMessage = "Error saving reabastecimento: ${e.message}") }
                } finally {
                    _uiState.update { it.copy(isLoading = false) }
                }
            } // End of with(_uiState.value) block
        }
    }
}





