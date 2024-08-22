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
            MutableStateFlow<Map<Long, List<Reabastecimento>>>(emptyMap())
        private val _veiculo = MutableStateFlow<Veiculo?>(null)
    }

    val reabastecimentos get() = _reabastecimentos.asStateFlow()

    val veiculo get() = _veiculo.asStateFlow()

    private val database = Automorama2Database(driver)

    fun getReabastecimentoByVeiculo(veiculoId: Long) {
        val reabastecimentos = database.getReabastecimetoByVeiculo(veiculoId).sortedBy { it.data }
        if (reabastecimentos.isNotEmpty())  _reabastecimentos.update { it + (veiculoId to reabastecimentos) }
    }

    fun saveReabastecimento(reabastecimento: Reabastecimento) {
        if (reabastecimento.id == null) {
            val id = database.setReabastecimento(reabastecimento)
            val newReabastecimento = reabastecimento.copy(id = id)
            _reabastecimentos.update {
                val updatedList = (it[reabastecimento.veiculo.id] ?: emptyList()) + newReabastecimento
                it + (reabastecimento.veiculo.id!! to updatedList.sortedBy { it.data })
            }
        } else {
            database.updateReabastecimento(reabastecimento)
            _reabastecimentos.update {
                it.mapValues { (veiculoId, reabastecimentos) ->
                    if (veiculoId == reabastecimento.veiculo.id) {
                        reabastecimentos.map { if (it.id == reabastecimento.id) reabastecimento else it }
                            .sortedBy { it.data }
                    } else {
                        reabastecimentos
                    }
                }
            }
        }
    }

    fun deleteReabastecimento(id: Long) {
        database.deleteReabastecimento(id)
        _reabastecimentos.update { map ->
            map.mapValues { (veiculoId, reabastecimentos) ->
                reabastecimentos.filterNot { it.id == id }
            }
        }
    }

    fun deleteReabastecimentoByVeiculo(veiculoId: Long) {
        database.deleteReabastecimentoByVeiculo(veiculoId)
        _reabastecimentos.update { map ->
            map.filterNot { it.key == veiculoId }
        }
    }

    fun deleteReabastecimentoByCombustivel(combustivelId: Long) {
        database.deleteReabastecimentoByCombustivel(combustivelId)
        _reabastecimentos.update { map ->
            map.mapValues { (veiculoId, reabastecimentos) ->
                reabastecimentos.filterNot { it.combustivel.id == combustivelId }
            }
        }
    }

    fun checkReabastecimentoByCombustivel(combustivelId: Long) : Boolean  = database.checkReabastecimentoByCombustivel(combustivelId)
}
