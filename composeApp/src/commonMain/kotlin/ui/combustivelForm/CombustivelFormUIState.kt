package ui.combustivelForm

data class CombustivelFormUIState(
    val nome: String = "",
    val onNomeChange: (String) -> Unit = {}
)