package data.models
data class Veiculo(
    val id: Long = 0,
    var marca: String = "",
    var modelo: String = "",
    var anoFabricacao: Long = 0,
    var anoModelo: Long = 0,
    var placa: String = "",
){
    var apelido: String = ""
}
