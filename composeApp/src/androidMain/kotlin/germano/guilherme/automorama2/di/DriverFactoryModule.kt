package germano.guilherme.automorama2.di

import app.cash.sqldelight.db.SqlDriver
import data.DriverFactory
import org.koin.dsl.module

val driverFactoryModule = module {
    single<SqlDriver> { DriverFactory(get()).createDriver() }
}