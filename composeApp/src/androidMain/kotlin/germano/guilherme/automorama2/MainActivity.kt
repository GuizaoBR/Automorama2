package germano.guilherme.automorama2

import App
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app = App()
        setContent {
//            enableEdgeToEdge(
//                statusBarStyle = SystemBarStyle.auto(
//                    Color.TRANSPARENT, Color.TRANSPARENT
//                )
//            )
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = !isSystemInDarkTheme()

            SideEffect {
                systemUiController.setSystemBarsColor(
                    color = if(!useDarkIcons) androidx.compose.ui.graphics.Color(0xFF141318) else androidx.compose.ui.graphics.Color.Transparent,
//                    color = androidx.compose.ui.graphics.Color.Transparent,
                    darkIcons = useDarkIcons
                )
            }
            ProvideWindowInsets{
                app.App(Modifier)
            }
        }
    }
}
