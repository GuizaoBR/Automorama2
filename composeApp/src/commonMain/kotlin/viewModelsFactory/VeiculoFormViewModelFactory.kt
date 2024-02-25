package viewModelsFactory

import viewModels.VeiculoFormViewModel
import interfaces.IVeiculoFormViewModelFactory

class VeiculoFormViewModelFactory: IVeiculoFormViewModelFactory {
    override fun create(id: Long?): VeiculoFormViewModel {
        return VeiculoFormViewModel(id)
    }

}