package repositoryFactory

import app.cash.sqldelight.db.SqlDriver
import data.repositories.VeiculoRepository
import interfaces.IVeiculoRepositoryFactory

class VeiculoRepositoryFactory(private val sqlDriver: SqlDriver): IVeiculoRepositoryFactory {
    override fun create(): VeiculoRepository {
        return VeiculoRepository(sqlDriver)
    }

}