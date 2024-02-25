package interfaces

import data.repositories.VeiculoRepository

interface IVeiculoRepositoryFactory {
    fun create(): VeiculoRepository
}