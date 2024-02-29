package plataform

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

actual class DeviceInfo(private val context: Context) {
    actual fun getScreenResolution(): Pair<Int, Int> {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            val display = windowManager.defaultDisplay
            display?.getRealMetrics(displayMetrics)
        } else {
            @Suppress("DEPRECATION")
            windowManager.defaultDisplay.getMetrics(displayMetrics)
        }

        return Pair(displayMetrics.widthPixels, displayMetrics.heightPixels)
    }
}