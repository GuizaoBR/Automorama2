package interfaces

import viewModels.ReabastecimentoListViewModel

interface IReabastecimentoListViewModelFactory {
    fun create(veiculoId: Long): ReabastecimentoListViewModel
}