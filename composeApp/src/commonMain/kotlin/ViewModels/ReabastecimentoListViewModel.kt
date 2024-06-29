package viewModels

import com.hoc081098.kmp.viewmodel.ViewModel
import data.models.Veiculo
import data.repositories.ReabastecimentoRepository
import data.repositories.VeiculoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repositoryFactory.ReabastecimentoRepositoryFactory
import repositoryFactory.VeiculoRepositoryFactory
import ui.reabastecimentos.ReabastecimentoListUIState

class ReabastecimentoListViewModel(
    private val veiculoId: Long
) : ViewModel(), KoinComponent {

    private val repositoryFactory: ReabastecimentoRepositoryFactory by inject()
    private val veiculoRepositoryFactory: VeiculoRepositoryFactory by inject()

    private val repository = repositoryFactory.create()
    private val veiculoRepository = veiculoRepositoryFactory.create()

    val _uiState: MutableStateFlow<ReabastecimentoListUIState> = MutableStateFlow(ReabastecimentoListUIState())

    val uiState: StateFlow<ReabastecimentoListUIState> = _uiState
        .stateIn(
            scope = viewModelScope,started = SharingStarted.WhileSubscribed(5000),
            initialValue = _uiState.value
        )
    init {
        _uiState.update {
            it.copy(veiculos = veiculoRepository.veiculos.value)
        }
        _uiState.update { currentState ->
            currentState.copy(
                onChangeVeiculo = { selectedVeiculo ->
                    _uiState.update { it.copy(veiculo = selectedVeiculo) }
                    _uiState.update {
                        repository.getReabastecimentoByVeiculo(selectedVeiculo.id!!)
                        it.copy(reabastecimentos = repository.reabastecimentos.value)
                    }

                }
            )
        }
    }


}