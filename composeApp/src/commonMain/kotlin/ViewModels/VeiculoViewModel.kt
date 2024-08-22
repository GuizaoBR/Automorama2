package viewModels

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.models.Reabastecimento
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repositoryFactory.ReabastecimentoRepositoryFactory
import repositoryFactory.VeiculoRepositoryFactory
import ui.veiculos.VeiculosListUiState

class VeiculoViewModel() : ScreenModel, KoinComponent {

    private val veiculoRepositoryFactory: VeiculoRepositoryFactory by inject()
    private val reabastecimentoRepositoryFactory: ReabastecimentoRepositoryFactory by inject()

    private val repository = veiculoRepositoryFactory.create()
    private val reabastecimentoRepository = reabastecimentoRepositoryFactory.create()

    private val _uiState: MutableStateFlow<VeiculosListUiState> =
        MutableStateFlow(VeiculosListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        repository.getVeiculos()
        observeData()
    }

    private fun observeData() {
        screenModelScope.launch {
            repository.veiculos.collect { veiculos ->
                val veiculosWithMedia = veiculos.map { veiculo ->
                    reabastecimentoRepository.getReabastecimentoByVeiculo(veiculo.id!!)
                    val reabastecimentosVeiculo =
                        reabastecimentoRepository.reabastecimentos.value[veiculo.id] ?: emptyList()
                    val mediaCombustivel = calculateMediaCombustivel(reabastecimentosVeiculo)
                    veiculo.copy(media = mediaCombustivel)
                }
                _uiState.value = VeiculosListUiState(
                    veiculos = veiculosWithMedia,
                    onDelete = { veiculo ->
                        val hasReabastecimentos =
                            reabastecimentoRepository.reabastecimentos.value.containsKey(veiculo.id)
                        if (hasReabastecimentos) {
                            reabastecimentoRepository.deleteReabastecimentoByVeiculo(veiculo.id!!)
                        }
                        repository.deleteVeiculo(veiculo)
                    },
                )
            }
        }

        screenModelScope.launch {
            reabastecimentoRepository.reabastecimentos.collect {
                _uiState.value = _uiState.value.copy(veiculos = _uiState.value.veiculos)
            }
        }
    }

    private fun calculateMediaCombustivel(reabastecimentos: List<Reabastecimento>): Double {
        if (reabastecimentos.isEmpty()) return 0.0

        val totalMedia = reabastecimentos.sumOf { it.quilometragemLitro }
        val qtd = reabastecimentos.count()
        return if (qtd > 0) totalMedia / qtd else 0.0
    }


}