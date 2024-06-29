package viewModelsFactory

import interfaces.IReabastecimentoFormViewModelFactory
import viewModels.ReabastecimentoFormViewModel

class ReabastecimentoFormViewModelFactory: IReabastecimentoFormViewModelFactory {
    override fun create(id: Long?, veiculoId: Long): ReabastecimentoFormViewModel {
        return ReabastecimentoFormViewModel(id, veiculoId)
    }
}