package ui.veiculoForm

data class VeiculoFormUIState (
    val fabricante: String = "",
    val modelo: String = "",
    val anoFabricacao: String = "",
    val anoModelo: String = "",
    val placa: String = "",
    val apelido: String = "",
    val topAppBarTitle: String = "",
    val onFabricanteChange: (String) -> Unit = {},
    val onModeloChange: (String) -> Unit = {},
    val onAnoFabricacaoChange: (String) -> Unit = {},
    val onAnoModeloChange: (String) -> Unit = {},
    val onPlacaChange: (String) -> Unit = {},
    val onApelidoChange: (String) -> Unit = {},
    val isValid: Boolean = false,
    val isPlateValid: Boolean = true,
    val isApelidoValid: Boolean = true,

)