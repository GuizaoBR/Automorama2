package interfaces

import data.repositories.CombustivelRepository

interface ICombustivelRepositoryFactory {
    fun create(): CombustivelRepository
}