package viewModels

import cafe.adriel.voyager.core.model.ScreenModel
import data.repositories.CombustivelRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repositoryFactory.CombustivelRepositoryFactory
import repositoryFactory.ReabastecimentoRepositoryFactory
import ui.combustiveis.CombustivelListUIState

class CombustiveisViewModel(): ScreenModel, KoinComponent {

    private val repositoryInjection: CombustivelRepositoryFactory by inject()
    private val reabastecimentoRepositoryFactory: ReabastecimentoRepositoryFactory by inject()

    private val repository: CombustivelRepository = repositoryInjection.create()
    private val reabastecimentoRepository = reabastecimentoRepositoryFactory.create()

    private val _uiState: MutableStateFlow<CombustivelListUIState> = MutableStateFlow(
        CombustivelListUIState()
    )

    val uiState
        get() = _uiState.combine(repository.combustiveis) { uiState, combustiveis ->
            uiState.copy(combustiveis = combustiveis,
                onDelete = { combustivel ->
                    val anyReabastecimento = reabastecimentoRepository.checkReabastecimentoByCombustivel(combustivel.id!!)

                    if (anyReabastecimento) reabastecimentoRepository.deleteReabastecimentoByCombustivel(combustivel.id!!)

                    repository.deleteCombustivel(combustivel)

                })
        }

    init {
        repository.getCombustiveis()

    }
}
 