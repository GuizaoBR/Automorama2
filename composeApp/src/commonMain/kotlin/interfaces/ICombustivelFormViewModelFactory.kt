package interfaces

import viewModels.CombustivelFormViewModel

interface ICombustivelFormViewModelFactory {
    fun create(id: Long?): CombustivelFormViewModel
}