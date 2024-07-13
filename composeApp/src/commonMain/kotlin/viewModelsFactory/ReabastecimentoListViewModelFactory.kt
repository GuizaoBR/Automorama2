package viewModelsFactory

import interfaces.IReabastecimentoListViewModelFactory
import viewModels.ReabastecimentoListViewModel

class ReabastecimentoListViewModelFactory():
    IReabastecimentoListViewModelFactory {
    override fun create(): ReabastecimentoListViewModel {
        return ReabastecimentoListViewModel()
    }

}