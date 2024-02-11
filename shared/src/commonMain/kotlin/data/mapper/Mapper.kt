package data.mapper

import data.models.Veiculo
import germano.guilherme.automorama2.Veiculos

fun Veiculos.toVeiculo(): Veiculo{
    val verifiedApelido = apelido?.let {
        it
    } ?:
     let {
        ""
    }
    return Veiculo(id, fabricante, modelo, anoFabricacao, anoModelo, placa, verifiedApelido
    )
}