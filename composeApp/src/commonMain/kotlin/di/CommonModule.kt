package di

import org.koin.dsl.module
import repositoryFactory.CombustivelRepositoryFactory
import repositoryFactory.VeiculoRepositoryFactory
import viewModelsFactory.CombustiveisViewModelFactory
import viewModelsFactory.CombustivelFormViewModelFactory
import viewModelsFactory.VeiculoFormViewModelFactory
import viewModelsFactory.VeiculosViewModelFactory

fun commonModule() = module {
    single<VeiculoRepositoryFactory> {
        VeiculoRepositoryFactory(get())
    }
    single<VeiculosViewModelFactory> {
        VeiculosViewModelFactory()
    }
    single<VeiculoFormViewModelFactory> {
        VeiculoFormViewModelFactory()
    }
    single<CombustiveisViewModelFactory> {
        CombustiveisViewModelFactory()
    }
    factory<CombustivelFormViewModelFactory> {
        CombustivelFormViewModelFactory()
    }
    single<CombustivelRepositoryFactory> {
        CombustivelRepositoryFactory(get())
    }


    
}
