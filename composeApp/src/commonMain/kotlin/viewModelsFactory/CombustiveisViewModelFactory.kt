package viewModelsFactory

import viewModels.CombustiveisViewModel
import interfaces.ICombustivelViewModelFactory

class CombustiveisViewModelFactory():
    ICombustivelViewModelFactory {
    override fun create(): CombustiveisViewModel {
        return CombustiveisViewModel()
    }
}