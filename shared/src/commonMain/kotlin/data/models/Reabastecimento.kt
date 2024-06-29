package data.models

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

data class Reabastecimento(
    val id: Long = 0,
    val combustivel: Combustivel = Combustivel(),
    val veiculo: Veiculo = Veiculo(),
    val valorTotal: Double = 0.0,
    val valorLitro: Double = 0.0,
    val litro: Double = 0.0,
    val data: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
    val quilometragemAnterior: Double = 0.0,
    val quilometragemAtual: Double = 0.0,
    val quilometragemLitro: Double = 0.0
) {
    public fun createReabastecimentosList(): List<Reabastecimento> {
        val reabastecimentoList = mutableListOf<Reabastecimento>()

        val combustivel = Combustivel(id = 1, nome = "Combustivel")
        val veiculo = Veiculo(
            id = 1,
            fabricante = "Fabricante",
            modelo = "Modelo",
            anoFabricacao = 2024L,
            anoModelo = 2024L,
            placa = "ABC-1234",
            apelido = "Apelido"
        )
        for (i in 1..10) { 

            val reabastecimento = Reabastecimento(
                id = i.toLong(),
                combustivel = combustivel,
                veiculo = veiculo,
                valorTotal = 100.0 * i,
                valorLitro = 10.0 * i,
                litro = 10.0 * i,
                data = Clock.System.todayIn(TimeZone.currentSystemDefault()),
                quilometragemAnterior = 1000.0 * i,
                quilometragemAtual = 1100.0 * i,
                quilometragemLitro = ((1100.0 * i) - (1000.0 * i)) / (10.0 * i)
            )
            reabastecimentoList.add(reabastecimento)
        }
        return reabastecimentoList
    }
}
