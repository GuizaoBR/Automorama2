package viewModels

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
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
): ScreenModel, KoinComponent {
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

        val reabatecimentosVehicle = repository.reabastecimentos.value.values.flatten().filter {
            it.veiculo.id == veiculoId
        }
        if(reabatecimentosVehicle.isNotEmpty()){
            val reabastecimentoAnterior = reabatecimentosVehicle.maxBy { it.data }
            _uiState.update { it.copy(quilometragemAnterior = reabastecimentoAnterior.quilometragemAtual.toString()) }
        }

        _uiState.update { currentState ->
            currentState.copy(
                onValorTotalChange = { valorTotal ->
                    _uiState.update { it.copy(valorTotal = valorTotal, isValid = checkIsValid()) }
                    if(_uiState.value.litro.isNotEmpty() && _uiState.value.litro != "\n"  ){
                        val valorLitro = (_uiState.value.litro.toDoubleOrNull()?.let {
                            valorTotal.toDoubleOrNull()
                                ?.div(it)
                        })?.round(2)
                        _uiState.update { it.copy(valorLitro = valorLitro.toString()) }
                    }
                },
                onValorLitroChange = { valorLitro ->
                    _uiState.update { it.copy(valorLitro = valorLitro) }
                    _uiState.update { it.copy(isValid = checkIsValid()) }

                },
                onLitroChange = { litro ->
                    _uiState.update { it.copy(litro = litro) }
                    _uiState.update { it.copy(isValid = checkIsValid()) }

                },
                onDataChange = { data ->
                    _uiState.update { it.copy(data = data) }
                    _uiState.update { it.copy(isValid = checkIsValid()) }
                },
                onQuilometragemAnteriorChange = { quilometragemAnterior ->
                    _uiState.update { it.copy(quilometragemAnterior = quilometragemAnterior) }
                    _uiState.update { it.copy(isValid = checkIsValid()) }

                },
                onQuilometragemAtualChange = { quilometragemAtual ->
                    _uiState.update { it.copy(quilometragemAtual = quilometragemAtual) }
                    _uiState.update { it.copy(isValid = checkIsValid(), isValidQuilometroAtual = checkQuilometragemAtualBiggerThanAnterior()) }

                },
                onQuilometroLitroChange = { quilometroLitro ->
                    _uiState.update { it.copy(quilometroLitro = quilometroLitro) }
                    _uiState.update { it.copy(isValid = checkIsValid()) }

                },
                onCombustivelChange = { combustivel ->
                    _uiState.update { it.copy(combustivel = combustivel) }
                    _uiState.update { it.copy(isValid = checkIsValid()) }
                },

            )
        }

        if (id != null) {
            screenModelScope.launch {
                val reabastecimento = repository.reabastecimentos.value.values.flatten().find {
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
                        )
                    }
                } else {
                    _uiState.update { it.copy(errorMessage = "Reabastecimento not found") }
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
                    combustivel != null &&
                    checkQuilometragemAtualBiggerThanAnterior()
        }
    }

    fun checkQuilometragemAtualBiggerThanAnterior(): Boolean{
        with(_uiState.value){
            if(quilometragemAnterior.isNotEmpty() && quilometragemAtual.isNotEmpty()
                && quilometragemAnterior.toDouble() >= quilometragemAtual.toDouble()){
                return false
            }
        }
        return true
    }


    fun saveReabastecimento() {
        screenModelScope.launch {

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

                } catch (e: Exception) {
                    _uiState.update { it.copy(errorMessage = "Error saving reabastecimento: ${e.message}") }
                }
            } // End of with(_uiState.value) block
        }
    }
}





