package data.mapper

import data.models.Veiculo
import germano.guilherme.automorama2.Veiculos

fun Veiculos.toVeiculo(): Veiculo{
    return Veiculo(id, fabricante, modelo, anoFabricacao, anoModelo, placa)
}