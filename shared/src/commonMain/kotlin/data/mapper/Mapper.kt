package data.mapper

import data.models.Combustivel
import data.models.Veiculo
import germano.guilherme.automorama2.Combustiveis
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

fun Combustiveis.toCombustivel() : Combustivel {
    val verifiedNome = nome?.let {
        it
    }?: let {
        ""
    }
    return Combustivel(id, verifiedNome)
}