package viewModels

import cafe.adriel.voyager.core.model.ScreenModel
import data.models.Combustivel
import data.repositories.CombustivelRepository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repositoryFactory.CombustivelRepositoryFactory
import ui.combustivelForm.CombustivelFormUIState

class CombustivelFormViewModel(
    private val id: Long?= null
) : ScreenModel, KoinComponent {
    private val combustivelRepositoryFactory: CombustivelRepositoryFactory by inject()
    private val combustivelRepository : CombustivelRepository = combustivelRepositoryFactory.create()
    private val _uiState: MutableStateFlow<CombustivelFormUIState> = MutableStateFlow(CombustivelFormUIState())
    
    val uiState = _uiState.asStateFlow()
    
    init {
        _uiState.update {
            it.copy(topAppBarTitle = "Novo Combustível")
        }
        _uiState.update { curresteState ->
            curresteState.copy(onNomeChange = {nome ->
                _uiState.update {
                    it.copy(nome = nome, nameAlreadyExist = checkNameAlreadyExists(nome))
                }
                _uiState.update {
                    it.copy(isValid = checkIsValid())
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
                        it.copy(nome = combustivel.nome, topAppBarTitle = "Editando Combustível")
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
    fun checkIsValid(): Boolean {
        return with(_uiState.value) {
            this.nome.isNotEmpty() && !checkNameAlreadyExists(this.nome)
        }
    }
    
    fun checkNameAlreadyExists(nome: String): Boolean {
        return combustivelRepository.combustiveis.value.any { it.nome == nome && it.id != id }
    }
}

