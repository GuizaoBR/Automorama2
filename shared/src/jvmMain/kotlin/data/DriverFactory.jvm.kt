package data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import germano.guilherme.automorama2.Automorama2Database
import java.io.File

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        val driver = JdbcSqliteDriver(url = "jdbc:sqlite:Automorama2Database.db")
        if (!File("Automorama2Database.db").exists()) {
            Automorama2Database.Schema.create(driver)
            createCombustiveis(driver)
        }
        return driver
    }
}