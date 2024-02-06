package ViewModels

import app.cash.sqldelight.db.SqlDriver
import data.getAll
import data.models.Veiculo
import data.setVeiculo
import germano.guilherme.automorama2.Automorama2Database
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import ui.Veiculos.VeiculosListUiState
import kotlin.random.Random

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


    fun save() {
        with(_uiState.value) {
            database.setVeiculo(
                Veiculo(
                    Random.nextLong(),
                    "Chevrolet",
                    "onix",
                    2023,
                    2024,
                    "abc1d23"
                )
            )
        }

    }
    init {
    }



//    private fun addVeiculo(veiculo: Veiculo) {
//        viewModelScope.launch {
//            database.setVeiculo(veiculo)
//            veiculos.value = database.getAll()
//        }
//    }
}