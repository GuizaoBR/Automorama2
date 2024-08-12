package data.repositories

import app.cash.sqldelight.db.SqlDriver
import data.deleteVeiculo
import data.getAllVeiculos
import data.getVeiculoById
import data.models.Veiculo
import data.setVeiculo
import data.updateVeiculo
import germano.guilherme.automorama2.Automorama2Database
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class VeiculoRepository(driver: SqlDriver) {

    companion object {
        private val _veiculos =
            MutableStateFlow<List<Veiculo>>(emptyList())
    }

    val veiculos get() = _veiculos.asStateFlow()
    private val database = Automorama2Database(driver)

    fun getVeiculos() = _veiculos.update {
        database.getAllVeiculos()
    }

    fun saveVeiculo(veiculo: Veiculo) {
        if (veiculo.id == null) {
            val id = database.setVeiculo(veiculo)
            val newVeiculo = veiculo.copy(id = id)
            _veiculos.update { it + newVeiculo } // Use '+' to create a new list
        } else {
            database.updateVeiculo(veiculo)
            _veiculos.update { currentList ->
                currentList.map { if (it.id == veiculo.id) veiculo else it }
            }
        }
    }

    fun deleteVeiculo(veiculo: Veiculo) {
        database.deleteVeiculo(veiculo.id!!)
        _veiculos.update { it - veiculo }
    }

    fun getVeiculosById(id: Long): Veiculo {
        return database.getVeiculoById(id)
    }
}