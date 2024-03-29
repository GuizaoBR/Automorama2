package data

import app.cash.sqldelight.db.SqlDriver
import data.mapper.toCombustivel
import data.mapper.toVeiculo
import data.models.Combustivel
import data.models.Veiculo
import germano.guilherme.automorama2.Automorama2Database

expect class DriverFactory {
    fun createDriver(): SqlDriver
}
fun createCombustiveis(driver: SqlDriver) {
    val database = Automorama2Database(driver)
    database.setCombustivel(Combustivel(nome = "Gasolina Comum"))
    database.setCombustivel(Combustivel(nome = "Gasolina Aditivada"))
    database.setCombustivel(Combustivel(nome = "Gasolina Premium"))
    database.setCombustivel(Combustivel(nome = "Etanol"))
}



fun Automorama2Database.setVeiculo(veiculo: Veiculo){
    return veiculosQueries.insertVeiculo(
        veiculo.id,
        veiculo.fabricante,
        veiculo.modelo,
        veiculo.anoFabricacao,
        veiculo.anoModelo,
        veiculo.placa,
        veiculo.apelido
    )
}


fun Automorama2Database.getAllVeiculos(): MutableList<Veiculo> {
    return veiculosQueries.selectAllVeiculos().executeAsList().map {
        it.toVeiculo()
    }.toMutableList()
}

fun Automorama2Database.updateVeiculo(veiculo: Veiculo) {
    return veiculosQueries.updateVeiculo(
        veiculo.fabricante,
        veiculo.modelo,
        veiculo.anoFabricacao,
        veiculo.anoModelo,
        veiculo.placa,
        veiculo.apelido,
        veiculo.id!!
    )
}

fun Automorama2Database.deleteVeiculo(id: Long) = veiculosQueries.deleteVeiculo(id)

fun Automorama2Database.setCombustivel(combustivel: Combustivel) = combustiveisQueries.insertCombustivei(combustivel.nome)
fun Automorama2Database.getAllCombustiveis(): MutableList<Combustivel> {
    return combustiveisQueries.selectAllCombustivel().executeAsList().map {
        it.toCombustivel()
    }.toMutableList()
}
fun Automorama2Database.updateCombustivel(combustivel: Combustivel) {
    return combustiveisQueries.updateCombustivei(
        nome = combustivel.nome,
        id = combustivel.id!!
    )
}

fun Automorama2Database.deleteCombustivel(id: Long) =  combustiveisQueries.deleteCombustivei(id)
