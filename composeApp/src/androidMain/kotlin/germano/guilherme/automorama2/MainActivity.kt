package germano.guilherme.automorama2

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import app.cash.sqldelight.db.SqlDriver
import data.DriverFactory
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val driverFactory: SqlDriver by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = App(
            driverFactory
        )
        setContent {
            app.MyApp()
        }
    }
}
