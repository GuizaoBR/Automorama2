package helpers

import kotlin.math.pow
import kotlin.math.roundToInt

fun Double.round(decimals: Int = 2): Double  {
    val factor = 10.0.pow(decimals)
    return (this * factor).roundToInt() / factor
}