package viewModels

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.hoc081098.kmp.viewmodel.ViewModel
import data.models.Veiculo
import data.repositories.ReabastecimentoRepository
import data.repositories.VeiculoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repositoryFactory.ReabastecimentoRepositoryFactory
import repositoryFactory.VeiculoRepositoryFactory
import ui.reabastecimentos.ReabastecimentoListUIState

class ReabastecimentoListViewModel(veiculoId: Long? = null): ScreenModel, KoinComponent {

    private val repositoryFactory: ReabastecimentoRepositoryFactory by inject()
    private val veiculoRepositoryFactory: VeiculoRepositoryFactory by inject()

    private val repository = repositoryFactory.create()
    private val veiculoRepository = veiculoRepositoryFactory.create()

    val _uiState: MutableStateFlow<ReabastecimentoListUIState> = MutableStateFlow(ReabastecimentoListUIState())
    val uiState: StateFlow<ReabastecimentoListUIState> = _uiState.asStateFlow()

    init {
        veiculoId?.let { veiculoId ->
            screenModelScope.launch {
                _uiState.update {
                    repository.getReabastecimentoByVeiculo(veiculoId)
                    val veiculo = veiculoRepository.getVeiculosById(veiculoId)
                    it.copy(veiculo = veiculo,
                        reabastecimentos = repository.reabastecimentos.value[veiculoId]?: listOf())
                }
            }
        }

        _uiState.update {
            it.copy(veiculos = veiculoRepository.veiculos.value,
                onDelete = { id ->
                    repository.deleteReabastecimento(id)
                    _uiState.update {
                        val updatedReabastecimentos = it.reabastecimentos.map { reabastecimento ->
                            if (reabastecimento.id == id) {
                                reabastecimento.copy(isVisibe = false)
                            } else {
                                reabastecimento
                            }
                        }
                        it.copy(reabastecimentos = updatedReabastecimentos)
                    }

                })
        }
        if (_uiState.value.veiculo != null) {
            _uiState.update {
                repository.getReabastecimentoByVeiculo(_uiState.value.veiculo!!.id!!)
                it.copy(reabastecimentos = repository.reabastecimentos.value[it.veiculo!!.id!!]?: listOf())
            }
        }





        _uiState.update { currentState ->
            currentState.copy(
                onChangeVeiculo = { selectedVeiculo ->
                    _uiState.update { it.copy(veiculo = selectedVeiculo) }
                    _uiState.update {
                        repository.getReabastecimentoByVeiculo(selectedVeiculo.id!!)
                        it.copy(reabastecimentos = repository.reabastecimentos.value[selectedVeiculo.id!!]?: listOf())
                    }

                }
            )
        }
        screenModelScope.launch {
            repository.reabastecimentos.collect { updatedReabastecimentos ->
                _uiState.update { currentState ->
                    currentState.copy(
                        reabastecimentos = updatedReabastecimentos[currentState.veiculo?.id] ?: listOf()
                    )
                }
            }
        }

    }

}