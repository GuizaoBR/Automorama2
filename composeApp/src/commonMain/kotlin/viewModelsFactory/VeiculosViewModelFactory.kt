package viewModelsFactory

import viewModels.VeiculoViewModel
import interfaces.IVeiculosViewModelFactory

class VeiculosViewModelFactory: IVeiculosViewModelFactory {
    override fun create(): VeiculoViewModel {
        return VeiculoViewModel()
    }

}