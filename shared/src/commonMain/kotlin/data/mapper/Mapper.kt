package data.mapper

import data.models.Veiculo
import germano.guilherme.automorama2.Veiculos

fun Veiculos.toVeiculo(): Veiculo{
    return Veiculo(id, marca, modelo, anoFabricacao, anoModelo, placa)
}