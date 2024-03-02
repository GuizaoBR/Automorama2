package data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import germano.guilherme.automorama2.Automorama2Database
import helper.DatabaseUtils.doesDatabaseExist
import platform.Foundation.NSFileManager
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask
import platform.Foundation.NSDocumentDirectory

actual class DriverFactory {
        actual fun createDriver(): SqlDriver {
                val exist = doesDatabaseExist("Automorama2Database.db")
                val nativeSqliteDriver =
                        NativeSqliteDriver(Automorama2Database.Schema, "Automorama2Database.db")
                if (!exist) {
                        createCombustiveis(nativeSqliteDriver)
                }
                return nativeSqliteDriver
        }
}



