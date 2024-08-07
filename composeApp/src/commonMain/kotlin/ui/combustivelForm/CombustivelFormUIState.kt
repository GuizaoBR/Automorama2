package ui.combustivelForm

data class CombustivelFormUIState(
    val nome: String = "",
    val onNomeChange: (String) -> Unit = {},
    val topAppBarTitle: String = "",
    val isValid: Boolean = false,
    val nameAlreadyExist: Boolean = false,
) {
}