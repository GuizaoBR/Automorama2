package repositoryFactory

import app.cash.sqldelight.db.SqlDriver
import data.repositories.CombustivelRepository
import interfaces.ICombustivelRepositoryFactory


class CombustivelRepositoryFactory(private val sqlDriver: SqlDriver): ICombustivelRepositoryFactory {
    override fun create(): CombustivelRepository {
        return CombustivelRepository(sqlDriver)
    }


}