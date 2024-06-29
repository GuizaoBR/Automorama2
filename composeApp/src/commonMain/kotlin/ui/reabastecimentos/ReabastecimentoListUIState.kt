package ui.reabastecimentos

import data.models.Reabastecimento
import data.models.Veiculo

data class ReabastecimentoListUIState(
    val reabastecimentos: List<Reabastecimento> = emptyList(),
    val onDelete: (Reabastecimento) -> Unit = {},
    val veiculo: Veiculo = Veiculo(),
    val veiculos: List<Veiculo> = emptyList(),
    val onChangeVeiculo: (Veiculo) -> Unit = {}

)
