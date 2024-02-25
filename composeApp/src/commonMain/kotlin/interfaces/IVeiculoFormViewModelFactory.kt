package interfaces

import viewModels.VeiculoFormViewModel

interface IVeiculoFormViewModelFactory {
    fun create(id: Long?): VeiculoFormViewModel
}