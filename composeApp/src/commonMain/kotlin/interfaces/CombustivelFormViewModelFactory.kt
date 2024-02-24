package interfaces

import ViewModels.CombustivelFormViewModel

interface CombustivelFormViewModelFactory {
    interface ViewModelFactory {
        fun create(id: Long?): CombustivelFormViewModel
    }
}