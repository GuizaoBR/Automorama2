package data.models
data class Veiculo(
    val id: Long,
    val marca: String,
    val modelo: String,
    val anoFabricacao: Long,
    val anoModelo: Long,
    val placa: String,
){
    val apelido: String = ""
}
