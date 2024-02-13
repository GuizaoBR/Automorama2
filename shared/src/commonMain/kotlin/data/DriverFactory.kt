package data

import app.cash.sqldelight.db.SqlDriver
import data.mapper.toVeiculo
import data.models.Veiculo
import germano.guilherme.automorama2.Automorama2Database

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


fun Automorama2Database.getAll(): MutableList<Veiculo> {
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
