package viewModels

import data.models.Combustivel
import data.repositories.CombustivelRepository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repositoryFactory.CombustivelRepositoryFactory
import ui.combustivelForm.CombustivelFormUIState

class CombustivelFormViewModel(
    private val id: Long?= null
) : ViewModel(), KoinComponent {
    private val combustivelRepositoryFactory: CombustivelRepositoryFactory by inject()
    private val combustivelRepository : CombustivelRepository = combustivelRepositoryFactory.create()
    private val _uiState: MutableStateFlow<CombustivelFormUIState> = MutableStateFlow(CombustivelFormUIState())
    
    val uiState = _uiState.asStateFlow()
    
    init {
        _uiState.update { curresteState ->
            curresteState.copy(onNomeChange = {nome ->
                _uiState.update {
                    it.copy(nome = nome)
                }
            })
        }
        id?.let {
            MainScope().launch {
                val combustivel = combustivelRepository.combustiveis.value
                    .find{
                        it.id == id
                    }
                if (combustivel != null) {
                    _uiState.update {
                        it.copy(nome = combustivel.nome)
                    }
                }
            }
        }
    }
    fun save() {
        with(_uiState.value) {
            combustivelRepository.saveCombustivel(
                Combustivel(
                    id= id,
                    nome = nome
                )
            )
        }
    }
}

