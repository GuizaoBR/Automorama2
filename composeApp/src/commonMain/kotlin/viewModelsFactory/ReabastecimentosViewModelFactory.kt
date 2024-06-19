package viewModelsFactory

import viewModels.CombustiveisViewModel
import interfaces.ICombustivelViewModelFactory

class ReabastecimentosViewModelFactory():
    ICombustivelViewModelFactory {
    override fun create(): CombustiveisViewModel {
        return CombustiveisViewModel()
    }
}