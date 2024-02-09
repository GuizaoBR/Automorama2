package data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.db.SqlDriver
import data.mapper.toVeiculo
import data.models.Veiculo
import germano.guilherme.automorama2.Automorama2Database
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

expect class DriverFactory {
    fun createDriver(): SqlDriver
}
fun createDatabase(driverFactory: DriverFactory) {
    val driver = driverFactory.createDriver()
    val database = Automorama2Database(driver)

    // Do more work with the database (see below).
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

fun Automorama2Database.getAll(): Flow<List<Veiculo>> {
    return veiculosQueries.selectAllVeiculos().asFlow().map {
        it.executeAsList().map { it.toVeiculo() }
    }
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