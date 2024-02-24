package viewModelsFactory

import ViewModels.CombustivelFormViewModel
import app.cash.sqldelight.db.SqlDriver
import interfaces.CombustivelFormViewModelFactory

class CombustivelFormViewModelFactory(private val driver: SqlDriver):
        CombustivelFormViewModelFactory.ViewModelFactory {
            override fun create(id: Long?): CombustivelFormViewModel {
                return CombustivelFormViewModel(driver, id)
            }
        }