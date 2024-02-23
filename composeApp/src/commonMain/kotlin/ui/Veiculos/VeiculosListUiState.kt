package ui.Veiculos

import data.models.Veiculo

data class VeiculosListUiState (
    val veiculos: List<Veiculo> = emptyList(),
    val onDelete: (Veiculo) -> Unit = {}
)