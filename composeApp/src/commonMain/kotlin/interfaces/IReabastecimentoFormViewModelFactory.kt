package interfaces

import viewModels.ReabastecimentoFormViewModel

interface IReabastecimentoFormViewModelFactory {
    fun create(id: Long?, veiculoId: Long): ReabastecimentoFormViewModel
}