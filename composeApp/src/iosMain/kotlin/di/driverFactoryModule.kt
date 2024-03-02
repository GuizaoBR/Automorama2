package di

import app.cash.sqldelight.db.SqlDriver
import data.DriverFactory
import org.koin.dsl.module

val driverFactoryModule = module {
    single<SqlDriver> { DriverFactory().createDriver() }
}
