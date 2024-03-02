package data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import germano.guilherme.automorama2.Automorama2Database
import platform.Foundation.NSFileManager
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask
import platform.Foundation.NSDocumentDirectory

actual class DriverFactory {
       actual fun createDriver(): SqlDriver {
        val paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, true)
        val documentsDirectory = paths[0] as String
        val databasePath = "$documentsDirectory/Automorama2Database.db"

        val exist = NSFileManager.defaultManager.fileExistsAtPath(databasePath)

               println(databasePath)
               println("$exist")
               val nativeSqliteDriver =
                       NativeSqliteDriver(Automorama2Database.Schema, "Automorama2Database.db")
               if (!exist) {
                       createCombustiveis(nativeSqliteDriver)
               }
               return nativeSqliteDriver
       }
}



