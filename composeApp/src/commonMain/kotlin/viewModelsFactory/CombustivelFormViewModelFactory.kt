package viewModelsFactory

import viewModels.CombustivelFormViewModel
import interfaces.ICombustivelFormViewModelFactory

class CombustivelFormViewModelFactory():
        ICombustivelFormViewModelFactory {
            override fun create(id: Long?): CombustivelFormViewModel {
                return CombustivelFormViewModel(id)
            }
        }