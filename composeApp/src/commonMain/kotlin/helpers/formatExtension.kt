package helpers

import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlin.math.pow
import kotlin.math.roundToInt

fun Double.round(decimals: Int = 2): Double  {
    val factor = 10.0.pow(decimals)
    return (this * factor).roundToInt() / factor
}

fun LocalDate.toBrazilFormat(): String{
    return this.format(LocalDate.Format {
        dayOfMonth()
        char('/')
        monthNumber()
        char('/')
        year()
    })
}

fun LocalDate.toBrazilFormatWithoutScape(): String{
    return this.format(LocalDate.Format {
        dayOfMonth()
        monthNumber()
        year()
    })

}

fun String.chekDateValidWithouScape(): Boolean{
    val format = LocalDate.Format {
        dayOfMonth()
        monthNumber()
        year()
    }
    try {
        LocalDate.parse(this, format)
        return true
    } catch(ex: IllegalArgumentException )  {
        return false
    }
}

