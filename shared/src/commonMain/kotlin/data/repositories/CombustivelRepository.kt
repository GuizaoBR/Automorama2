package data.repositories

import app.cash.sqldelight.db.SqlDriver
import data.deleteCombustivel
import data.getAllCombustiveis
import data.models.Combustivel
import data.setCombustivel
import data.updateCombustivel
import germano.guilherme.automorama2.Automorama2Database
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CombustivelRepository(driver: SqlDriver) {
    companion object {
        private val _combustiveis =
            MutableStateFlow<List<Combustivel>>(emptyList())
    }

    val combustiveis get() = _combustiveis.asStateFlow()
    private val database = Automorama2Database(driver)

    fun getCombustiveis() = _combustiveis.update {
        database.getAllCombustiveis()
    }

    fun saveCombustivel(combustivel: Combustivel){
        _combustiveis.update { list ->
            if(combustivel.id == null) {
                val id = database.setCombustivel(combustivel)
                val newCombustivel = combustivel.copy(id = id)
                list + newCombustivel
            } else {
                database.updateCombustivel(combustivel)
                list.map { if (it.id == combustivel.id) combustivel else it}}
        }
    }
    

    
    fun deleteCombustivel(combustivel: Combustivel) {
        database.deleteCombustivel(combustivel.id!!)
         _combustiveis.update { list ->
             list - combustivel
         }
    }

}