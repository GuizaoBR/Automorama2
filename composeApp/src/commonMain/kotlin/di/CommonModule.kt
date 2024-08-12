package di

import org.koin.dsl.module
import repositoryFactory.CombustivelRepositoryFactory
import repositoryFactory.ReabastecimentoRepositoryFactory
import repositoryFactory.VeiculoRepositoryFactory
import viewModelsFactory.CombustiveisViewModelFactory
import viewModelsFactory.CombustivelFormViewModelFactory
import viewModelsFactory.ReabastecimentoFormViewModelFactory
import viewModelsFactory.ReabastecimentoListViewModelFactory
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
    single<CombustivelFormViewModelFactory> {
        CombustivelFormViewModelFactory()
    }
    single<CombustivelRepositoryFactory> {
        CombustivelRepositoryFactory(get())
    }
    single<ReabastecimentoRepositoryFactory> {6
        ReabastecimentoRepositoryFactory(get())
    }
    factory<ReabastecimentoListViewModelFactory> {
        ReabastecimentoListViewModelFactory()

    }
    single<ReabastecimentoFormViewModelFactory> {
        ReabastecimentoFormViewModelFactory()
    }


    
}
