package data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import germano.guilherme.automorama2.Automorama2Database

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        val nativeSqliteDriver =
            NativeSqliteDriver(Automorama2Database.Schema, "Automorama2Database.db")
        createCombustiveis(nativeSqliteDriver)
        return nativeSqliteDriver
    }
}