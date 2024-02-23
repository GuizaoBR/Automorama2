package ViewModels

import data.repositories.CombustivelRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import ui.Combustiveis.CombustivelListUIState

class CombustiveisViewModel(private val repository: CombustivelRepository) {
    private  val _uiState: MutableStateFlow<CombustivelListUIState> = MutableStateFlow(
        CombustivelListUIState()
    )

    val uiState
        get() = _uiState.combine(repository.combustiveis){ uiState, combustiveis ->
            uiState.copy(combustiveis = combustiveis,
                         onDelete= {combustivel ->
                                 repository.deleteCombustivel(combustivel)
                             
                         })
        }
    init {
        repository.getCombustiveis()

    }
}
 