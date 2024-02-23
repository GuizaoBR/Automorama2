package ui.Combustiveis

import data.models.Combustivel
import germano.guilherme.automorama2.Combustiveis

data class CombustivelListUIState(
    val combustiveis: List<Combustivel> = emptyList(),
    val onDelete: (Combustivel) -> Unit = {}

)
