package data.models

import helpers.toStringeWithCommaFormat

data class Veiculo(
    val id: Long? = 0,
    var fabricante: String = "",
    var modelo: String = "",
    var anoFabricacao: Long = 0,
    var anoModelo: Long = 0,
    var placa: String = "",
    var apelido: String = "",
    var media: Double = 0.0
){
    fun ShowIdentifier(): String{
        if (apelido.isNotEmpty()){
            return apelido
        }
        return "$modelo - $placa"
    }
    fun ShowMedia(): String{
        return media.toStringeWithCommaFormat()
    }

}