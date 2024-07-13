package interfaces

import viewModels.ReabastecimentoListViewModel

interface IReabastecimentoListViewModelFactory {
    fun create(): ReabastecimentoListViewModel
}