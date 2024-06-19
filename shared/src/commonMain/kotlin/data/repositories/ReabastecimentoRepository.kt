package data.repositories

import app.cash.sqldelight.db.SqlDriver
import data.*
import data.models.Reabastecimento
import germano.guilherme.automorama2.Automorama2Database
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ReabastecimentoRepository(driver: SqlDriver) {

    companion object {
        private val _reabastecimentos =
            MutableStateFlow<MutableList<Reabastecimento>>(mutableListOf())
    }

    val reabastecimentos get() = _reabastecimentos.asStateFlow()
    private val database = Automorama2Database(driver)

    fun getReabastecimentoByVeiculo(veiculoid: Long) = _reabastecimentos.update {
        database.getReabastecimetoByVeiculo(veiculoid)
    }

    fun saveReabastecimento(reabastecimento: Reabastecimento){
        _reabastecimentos.update { listReabastecimento ->
            if (reabastecimento.id == 0L){
                database.setReabastecimento(reabastecimento)
                listReabastecimento.add(reabastecimento)
            } else {
                database.updateReabastecimento(reabastecimento)
//                listReabastecimento.find { it.id == reabastecimento.id }?.let {
//                    it.fabricante = veiculo.fabricante
//                    it.modelo = veiculo.modelo
//                    it.anoFabricacao = veiculo.anoFabricacao
//                    it.anoModelo = veiculo.anoModelo
//                    it.placa = veiculo.placa
//                    it.apelido = veiculo.apelido
//                }
            }
            listReabastecimento
        }
    }

    fun deleteReabastecimento(reabastecimento: Reabastecimento) {
        _reabastecimentos.update {
            database.deleteReabastecimentio(reabastecimento.id)
            it.remove(reabastecimento)
            it
        }
    }
}