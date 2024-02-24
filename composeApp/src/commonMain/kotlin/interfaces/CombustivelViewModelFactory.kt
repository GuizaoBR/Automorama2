package interfaces

import ViewModels.CombustiveisViewModel

interface CombustivelViewModelFactory {
    interface ViewModelFactory {
        fun create(): CombustiveisViewModel
    }
}