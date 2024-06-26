package data.mapper

import data.models.Combustivel
import data.models.Reabastecimento
import data.models.Veiculo
import germano.guilherme.automorama2.SelectReabastecimentosByVeiculo
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.char
import migrations.Combustiveis
import migrations.Reabastecimentos
import migrations.Veiculos

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

fun Reabastecimentos.toReabastecimento(combustivel: Combustivel, veiculo: Veiculo) : Reabastecimento {
    val dateFormat = LocalDate.Format {
        dayOfMonth()
        char('/')
        monthNumber()
        char('/')
        year()
    }
    val dataFormated = dateFormat.parse(data_!!)
    return Reabastecimento(id, combustivel, veiculo, valorTotal!!, valorLitro!!, litro!!, dataFormated, quilometragemAnterior!!, quilometragemAtual!! )
}

fun SelectReabastecimentosByVeiculo.toReabastecimento(): Reabastecimento {
    val combustivel = Combustivel(combustivelId, nome!!)
    val verifiedApelido = apelido ?:
    let {
        ""
    }
    val veiculo = Veiculo(veiculoId, fabricante, modelo, anoFabricacao, anoModelo, placa, verifiedApelido)
    val dateFormat = LocalDate.Format {
        dayOfMonth()
        char('/')
        monthNumber()
        char('/')
        year()
    }
    val dataFormated = LocalDate.parse(data_!!)
    return Reabastecimento(id, combustivel, veiculo, valorTotal!!, valorLitro!!, litro!!, dataFormated, quilometragemAnterior!!, quilometragemAtual!!,
        if (quilometragemLitro == null) 0.0 else quilometragemLitro)
}