package data.repositories

import app.cash.sqldelight.db.SqlDriver
import data.deleteVeiculo
import data.getAllVeiculos
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
            MutableStateFlow<MutableList<Veiculo>>(mutableListOf())
    }

    val veiculos get() = _veiculos.asStateFlow()
    private val database = Automorama2Database(driver)

    fun getVeiculos() = _veiculos.update {
        database.getAllVeiculos()
    }

    fun saveVeiculo(veiculo: Veiculo){
        _veiculos.update { listVeiculo ->
            if (veiculo.id == null){
                database.setVeiculo(veiculo)
                listVeiculo.add(veiculo)
            } else {
                database.updateVeiculo(veiculo)
                listVeiculo.find { it.id == veiculo.id }?.let {
                    it.fabricante = veiculo.fabricante
                    it.modelo = veiculo.modelo
                    it.anoFabricacao = veiculo.anoFabricacao
                    it.anoModelo = veiculo.anoModelo
                    it.placa = veiculo.placa
                    it.apelido = veiculo.apelido
                }
            }
            listVeiculo
        }
    }

    fun deleteVeiculo(veiculo: Veiculo) {
        _veiculos.update {
            database.deleteVeiculo(veiculo.id!!)
            it.remove(veiculo)
            it
        }
    }
}