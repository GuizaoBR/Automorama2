package viewModels

import cafe.adriel.voyager.core.model.ScreenModel
import com.hoc081098.kmp.viewmodel.ViewModel
import data.repositories.CombustivelRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repositoryFactory.CombustivelRepositoryFactory
import ui.Combustiveis.CombustivelListUIState

class CombustiveisViewModel(): ScreenModel, KoinComponent {

    private val repositoryInjection: CombustivelRepositoryFactory by inject()
    private val repository: CombustivelRepository = repositoryInjection.create()
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
 