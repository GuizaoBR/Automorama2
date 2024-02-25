package interfaces

import viewModels.VeiculoViewModel

interface IVeiculosViewModelFactory {
    fun create(): VeiculoViewModel
}