package data

import app.cash.sqldelight.db.SqlDriver
import data.mapper.toCombustivel
import data.mapper.toReabastecimento
import data.mapper.toVeiculo
import data.models.Combustivel
import data.models.Reabastecimento
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



fun Automorama2Database.setVeiculo(veiculo: Veiculo): Long{
     veiculosQueries.insertVeiculo(
        veiculo.id,
        veiculo.fabricante,
        veiculo.modelo,
        veiculo.anoFabricacao,
        veiculo.anoModelo,
        veiculo.placa,
        veiculo.apelido
    )
    return veiculosQueries.lastInsertRowId().executeAsOne()
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

fun Automorama2Database.getVeiculoById(id: Long): Veiculo {
    return veiculosQueries.getVeiculosById(id).executeAsOne().toVeiculo()
}


fun Automorama2Database.deleteVeiculo(id: Long) = veiculosQueries.deleteVeiculo(id)

fun Automorama2Database.setCombustivel(combustivel: Combustivel): Long {
    combustiveisQueries.insertCombustivei(combustivel.nome)
    return combustiveisQueries.lastInsertRowId().executeAsOne()
}
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


fun Automorama2Database.getReabastecimetoByVeiculo(veiculoId: Long): MutableList<Reabastecimento>{
    return reabastecimentosQueries.selectReabastecimentosByVeiculo(veiculoId).executeAsList().map {
        it.toReabastecimento()
    }.toMutableList()

}

fun Automorama2Database.updateReabastecimento(reabastecimento: Reabastecimento){
    return reabastecimentosQueries.updateReabastecimento(
        reabastecimento.combustivel.id,
        reabastecimento.veiculo.id,
        reabastecimento.valorTotal,
        reabastecimento.valorLitro,
        reabastecimento.litro,
        reabastecimento.data.toString(),
        reabastecimento.quilometragemAnterior,
        reabastecimento.quilometragemAtual,
        reabastecimento.quilometragemLitro,
        reabastecimento.id?: 0
    )
}
fun Automorama2Database.deleteReabastecimento(id: Long) = reabastecimentosQueries.deleteReabastecimento(id)

fun Automorama2Database.deleteReabastecimentoByVeiculo(veiculoId: Long) = reabastecimentosQueries.deleteReabastecimentosByVeiculo(veiculoId)

fun Automorama2Database.deleteReabastecimentoByCombustivel(combustivelId: Long) = reabastecimentosQueries.deleteReabastecimentosByCombustivel(combustivelId)

fun Automorama2Database.checkReabastecimentoByCombustivel(combustivelId: Long): Boolean = reabastecimentosQueries.checkReabastecimentoByCombustivel(combustivelId).executeAsOneOrNull() == 1L

fun Automorama2Database.setReabastecimento(reabastecimento: Reabastecimento): Long{
    reabastecimentosQueries.insertReabastecimento(
        reabastecimento.combustivel.id,
        reabastecimento.veiculo.id,
        reabastecimento.valorTotal,
        reabastecimento.valorLitro,
        reabastecimento.litro,
        reabastecimento.data.toString(),
        reabastecimento.quilometragemAnterior,
        reabastecimento.quilometragemAtual,
        reabastecimento.quilometragemLitro
    )
    val id = reabastecimentosQueries.lastInsertRowIdReabastecimento().executeAsOne()
    return id
}

