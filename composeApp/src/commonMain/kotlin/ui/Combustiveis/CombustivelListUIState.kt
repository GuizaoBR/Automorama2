package ui.Combustiveis

import data.models.Combustivel

data class CombustivelListUIState(
    val combustiveis: List<Combustivel> = emptyList(),
    val onDelete: (Combustivel) -> Unit = {}

)
