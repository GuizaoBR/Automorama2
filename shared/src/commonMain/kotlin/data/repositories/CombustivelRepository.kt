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
            MutableStateFlow<MutableList<Combustivel>>(mutableListOf())
    }

    val combustiveis get() = _combustiveis.asStateFlow()
    private val database = Automorama2Database(driver)

    fun getCombustiveis() = _combustiveis.update {
        database.getAllCombustiveis()
    }

    fun saveCombustivel(combustivel: Combustivel){
        _combustiveis.update { list ->
            if(combustivel.id == null) {
                database.setCombustivel(combustivel)
                list.add(combustivel)
            } else {
                database.updateCombustivel(combustivel)
                list.find {
                    it.id == combustivel.id
                }?.let {
                    it.nome == combustivel.nome
                }
            }
            list
        }
    }
    

    
    fun deleteCombustivel(combustivel: Combustivel) {
         _combustiveis.update { list ->
             database.deleteCombustivel(combustivel.id!!)
             list.remove(combustivel)
             list
         }
    }

}