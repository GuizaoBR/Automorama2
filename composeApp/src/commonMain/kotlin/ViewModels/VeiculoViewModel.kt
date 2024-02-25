package viewModels

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import moe.tlaster.precompose.viewmodel.ViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repositoryFactory.VeiculoRepositoryFactory
import ui.Veiculos.VeiculosListUiState

class VeiculoViewModel() : ViewModel(), KoinComponent{

    private val veiculoRepositoryFactory: VeiculoRepositoryFactory by inject()

    private val repository = veiculoRepositoryFactory.create()
    private  val _uiState: MutableStateFlow<VeiculosListUiState> = MutableStateFlow(
        VeiculosListUiState()
    )

    val uiState
        get() = _uiState.combine(repository.veiculos){ uiState, veiculos ->
            uiState.copy(veiculos = veiculos, onDelete={ veiculo ->
                repository.deleteVeiculo(veiculo)
            })
        }
    init {
        repository.getVeiculos()
    }


}