package helpers

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class WindowSize {
  COMPACT,
  MEDIUM,
  LARGE;

  // Factory method that creates an instance of the class based on window width
    companion object {
        fun screenSizeWidth(windowWidth: Dp): WindowSize {
            return when {
                windowWidth < 560.dp -> COMPACT
                windowWidth < 840.dp -> MEDIUM
                else -> LARGE
            }
        }
      fun screenSizeHeight(windowHeight: Dp): WindowSize {
          return when {
              windowHeight < 480.dp -> COMPACT
              windowHeight < 900.dp -> MEDIUM
              else -> LARGE
          }
      }
    }
}