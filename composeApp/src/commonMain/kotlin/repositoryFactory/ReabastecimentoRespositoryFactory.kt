package repositoryFactory

import app.cash.sqldelight.db.SqlDriver
import data.repositories.ReabastecimentoRepository
import interfaces.IReabastecimentoRepositoryFactory

class ReabastecimentoRepositoryFactory(private val sqlDriver: SqlDriver): IReabastecimentoRepositoryFactory {
    override fun create(): ReabastecimentoRepository {
        return ReabastecimentoRepository(sqlDriver)
    }
}