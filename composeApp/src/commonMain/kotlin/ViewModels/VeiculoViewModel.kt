package ViewModels

import app.cash.sqldelight.db.SqlDriver
import data.getAll
import data.repositories.VeiculoRepository
import germano.guilherme.automorama2.Automorama2Database
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import moe.tlaster.precompose.viewmodel.ViewModel
import ui.Veiculos.VeiculosListUiState

class VeiculoViewModel(private val repository: VeiculoRepository) : ViewModel(){

    private  val _uiState: MutableStateFlow<VeiculosListUiState> = MutableStateFlow(
        VeiculosListUiState()
    )

    val uiState
        get() = _uiState.combine(repository.veiculos){ uiState, veiculos ->
            uiState.copy(veiculos = veiculos)
        }
    init {
        repository.getVeiculos()
    }


}