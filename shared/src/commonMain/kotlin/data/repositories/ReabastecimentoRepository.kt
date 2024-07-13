package data.repositories

import app.cash.sqldelight.db.SqlDriver
import data.*
import data.models.Reabastecimento
import data.models.Veiculo
import germano.guilherme.automorama2.Automorama2Database
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ReabastecimentoRepository(driver: SqlDriver) {

    companion object {
        private val _reabastecimentos =
            MutableStateFlow<MutableList<Reabastecimento>>(mutableListOf())
        private val _veiculo = MutableStateFlow<Veiculo?>(null)
    }

    val reabastecimentos get() = _reabastecimentos.asStateFlow()

    val veiculo get() = _veiculo.asStateFlow()

    private val database = Automorama2Database(driver)

    fun getReabastecimentoByVeiculo(veiculoid: Long)  {
        _reabastecimentos.update {
            database.getReabastecimetoByVeiculo(veiculoid).sortedBy { it.data }.toMutableList()
        }
        _veiculo.update {
            database.getVeiculoById(veiculoid)
        }
    }

    fun saveReabastecimento(reabastecimento:Reabastecimento) {
        if (reabastecimento.id == null) {
            val id = database.setReabastecimento(reabastecimento)
            val newReabastecimento = reabastecimento.copy(id = id)
            _reabastecimentos.value =
                (_reabastecimentos.value + newReabastecimento).toMutableList() // Append new item
        } else {
            database.updateReabastecimento(reabastecimento)
            _reabastecimentos.value = _reabastecimentos.value.map {
                if(it.id == reabastecimento.id) reabastecimento else it // Update existing item
            }.toMutableList()
        }
    }

    fun deleteReabastecimento(id: Long) {
        _reabastecimentos.update {
            database.deleteReabastecimentio(id)
            it.remove(it.find { reabastecimento ->
                reabastecimento.id == id
            })
            it
        }
    }
}