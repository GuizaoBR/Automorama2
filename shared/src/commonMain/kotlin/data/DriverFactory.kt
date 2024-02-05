package data

import app.cash.sqldelight.db.SqlDriver
import data.mapper.toVeiculo
import data.models.Veiculo
import germano.guilherme.automorama2.Automorama2Database
import germano.guilherme.automorama2.Veiculos

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
        veiculo.marca,
        veiculo.modelo,
        veiculo.anoFabricacao,
        veiculo.anoModelo,
        veiculo.placa,
        veiculo.apelido
    )
}

fun Automorama2Database.getAll(): List<Veiculo> {
    return veiculosQueries.selectAllVeiculos().executeAsList().map {
        it.toVeiculo()
    }
}

fun Automorama2Database.updateVeiculo(veiculo: Veiculo) {
    return veiculosQueries.updateVeiculo(
        veiculo.marca,
        veiculo.modelo,
        veiculo.anoFabricacao,
        veiculo.anoModelo,
        veiculo.placa,
        veiculo.apelido,
        veiculo.id
    )
}