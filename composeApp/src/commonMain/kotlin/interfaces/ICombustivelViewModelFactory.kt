package interfaces

import viewModels.CombustiveisViewModel

interface ICombustivelViewModelFactory {
    fun create(): CombustiveisViewModel
}