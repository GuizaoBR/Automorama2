package helpers

fun filterNumbersAndDecimal(text: String): String {
    return text.filter { it.isDigit() || it == '.' || it == ',' }.replace(Regex("([.,])(\\1+)"), "$1") // Allow only one decimal point
}


val dayRegex = Regex("^(0[1-9]|[12]\\d|3[01])$")
val monthRegex = Regex("^(0[1-9]|1[0-2])$")
val yearRegex = Regex("^\\d{4}$")

fun isValidDay(day: String): Boolean {
    return dayRegex.matches(day)
}
fun isValidMonth(month: String): Boolean {
    return monthRegex.matches(month)
}
fun isValidYear(year: String): Boolean {
    return yearRegex.matches(year)
}
fun filterDateBrazilRegex(text: String): String {

    val digits = text.filter { it.isDigit() }.take(8)
    var day = ""
    var month = ""
    var year = ""



    if (digits.isNotEmpty()) {
        if (digits.length == 1){
            if (digits.toInt() > 3)
                return ""
        }
        day = if (digits.length >= 2 && dayRegex.matches(digits.substring(0, 1))) {
            digits.substring(0, 1)
        } else {
            ""
        }

        month = if (digits.length >= 3 ) {
            if(digits.length == 3 && digits.substring(1,2).toInt() <= 1){
                return digits.substring(1,2)
            } else if (monthRegex.matches(digits.substring(1, 3))) {
                digits.substring(1, 3)
            }
            else { "" }
        } else {
            ""}

        year = if (digits.length == 8 && yearRegex.matches(digits.substring(4, 8))) {
            digits.substring(4, 8)
        } else {
            ""
        }
    }
    val finalValue = if (day.isEmpty()) digits else  "$day$month$year"
    return finalValue


}