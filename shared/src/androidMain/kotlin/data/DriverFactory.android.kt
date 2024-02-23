package data

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import germano.guilherme.automorama2.Automorama2Database
import java.io.File

actual class DriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        val file = File(context.getDatabasePath("Automorama2Database.db").path)
        val androidSqliteDriver =
            AndroidSqliteDriver(Automorama2Database.Schema, context, "Automorama2Database.db")
        if(!file.exists()){
            createCombustiveis(androidSqliteDriver)
        }
        return androidSqliteDriver
    }
}