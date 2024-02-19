package data

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import germano.guilherme.automorama2.Automorama2Database

actual class DriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        val androidSqliteDriver =
            AndroidSqliteDriver(Automorama2Database.Schema, context, "Automorama2Database.db")
        createCombustiveis(androidSqliteDriver)
        return androidSqliteDriver
    }
}