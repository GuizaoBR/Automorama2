package ViewModels

import app.cash.sqldelight.db.SqlDriver
import data.getAll
import germano.guilherme.automorama2.Automorama2Database
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import moe.tlaster.precompose.viewmodel.ViewModel
import ui.Veiculos.VeiculosListUiState

class VeiculoViewModel(driver: SqlDriver) : ViewModel(){
//    val veiculos = MutableStateFlow<List<Veiculo>>(emptyList())

    private  val _uiState: MutableStateFlow<VeiculosListUiState> = MutableStateFlow(
        VeiculosListUiState()
    )

    val uiState
        get() = _uiState.combine(database.getAll()){ uiState, veiculos ->
            uiState.copy(veiculos = veiculos)
        }
    private val database = Automorama2Database(driver)


}