package ui.reabastecimentos

import data.models.Reabastecimento
import data.models.Veiculo

data class ReabastecimentoListUIState(
    val reabastecimentos: List<Reabastecimento> = emptyList(),
    val onDelete: (id: Long) -> Unit = {},
    val veiculo: Veiculo? = null,
    val veiculos: List<Veiculo> = emptyList(),
    val onChangeVeiculo: (Veiculo) -> Unit = {}
    


)
