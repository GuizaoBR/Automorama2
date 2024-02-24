package viewModelsFactory

import ViewModels.CombustiveisViewModel
import app.cash.sqldelight.db.SqlDriver
import interfaces.CombustivelViewModelFactory

class CombustiveisViewModelFactory(private val driver: SqlDriver):
    CombustivelViewModelFactory.ViewModelFactory {
    override fun create(): CombustiveisViewModel {
        return CombustiveisViewModel(driver)
    }
}