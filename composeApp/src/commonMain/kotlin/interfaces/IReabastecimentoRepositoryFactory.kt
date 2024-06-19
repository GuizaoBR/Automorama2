package interfaces

import data.repositories.ReabastecimentoRepository

interface IReabastecimentoRepositoryFactory {
    fun create(): ReabastecimentoRepository
}