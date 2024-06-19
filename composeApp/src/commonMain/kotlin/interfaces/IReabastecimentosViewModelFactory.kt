package interfaces

import viewModels.VeiculoViewModel

interface IReabastecimentoViewModelFactory {
    fun create(): VeiculoViewModel
}