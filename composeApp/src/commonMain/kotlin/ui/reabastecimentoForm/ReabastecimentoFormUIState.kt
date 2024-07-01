package ui.reabastecimentoForm

import data.models.Combustivel
import data.models.Veiculo

data class ReabastecimentoFormUIState(
    val id: Long? = null,
    val combustivel: Combustivel = Combustivel(),
    val veiculoId: Long = 0,
    val valorTotal: String = "",
    val valorLitro: String = "",
    val litro: String = "",
    val data: String = "", // Use LocalDate directly
    val quilometragemAnterior: String = "",
    val quilometragemAtual: String = "",
    val quilometroLitro: String = "",
    val combustiveis: List<Combustivel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val topAppBarTitle: String = "",
    val isValid: Boolean = false,
    val onValorTotalChange:(String) -> Unit = {},
    val onValorLitroChange: (String) -> Unit = {},
    val onLitroChange: (String) -> Unit = {},
    val onDataChange: (String) -> Unit = {},
    val onQuilometragemAnteriorChange: (String) -> Unit = {},
    val onQuilometragemAtualChange: (String) -> Unit = {},
    val onQuilometroLitroChange: (String) -> Unit = {},
    val onCombustivelChange: (Combustivel) -> Unit = {},
    val onVeiculoChange: (Veiculo) -> Unit = {},
    val onSaveClick: () -> Unit = {},
)
