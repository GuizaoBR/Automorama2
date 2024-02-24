package di

import ViewModels.CombustiveisViewModel
import ViewModels.CombustivelFormViewModel
import ViewModels.VeiculoFormViewModel
import ViewModels.VeiculoViewModel
import data.repositories.CombustivelRepository
import data.repositories.VeiculoRepository
import org.koin.dsl.module
import viewModelsFactory.CombustiveisViewModelFactory
import viewModelsFactory.CombustivelFormViewModelFactory

fun commonModule() = module {
    single {
        VeiculoRepository(get())
    }
    single<CombustiveisViewModelFactory> {
        CombustiveisViewModelFactory(get())
    }
    single<CombustivelFormViewModelFactory> {
        CombustivelFormViewModelFactory(get())
    }
    single {
        VeiculoViewModel(get())
    }
    single {
        CombustiveisViewModel(get())
    }
    single {
        VeiculoFormViewModel(repository = get(), id = null)
    }
    
}
