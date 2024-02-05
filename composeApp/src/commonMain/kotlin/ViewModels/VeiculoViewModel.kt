package ViewModels

import app.cash.sqldelight.db.SqlDriver
import data.getAll
import data.models.Veiculo
import data.setVeiculo
import germano.guilherme.automorama2.Automorama2Database
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class VeiculoViewModel(driver: SqlDriver) : ViewModel(){
    val veiculos = MutableStateFlow<List<Veiculo>>(emptyList())

    private val database = Automorama2Database(driver)

    init {
        getVeiculos()
    }

    private fun getVeiculos() {
        viewModelScope.launch {
            veiculos.value = database.getAll()
        }
    }

    private fun addVeiculo(veiculo: Veiculo) {
        viewModelScope.launch {
            database.setVeiculo(veiculo)
            veiculos.value = database.getAll()
        }
    }
}