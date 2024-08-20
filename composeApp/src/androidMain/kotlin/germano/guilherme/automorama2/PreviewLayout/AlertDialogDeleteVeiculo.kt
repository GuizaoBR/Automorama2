package germano.guilherme.automorama2.PreviewLayout

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import data.models.Veiculo
import ui.veiculos.AlertDeleteVeiculo

@Preview
@Composable
fun AlertDialogDeleteVeiculoPreview() {
    AlertDeleteVeiculo(veiculo = Veiculo(fabricante = "Chrevolet", modelo = "Onix", anoModelo = 2024, anoFabricacao = 2025, placa = "ABC1D23"))
}
@Preview
@Composable
fun AlertDialogDeleteVeiculoPreviewApelido() {
    AlertDeleteVeiculo(veiculo = Veiculo(fabricante = "Chrevolet", modelo = "Onix", anoModelo = 2024, anoFabricacao = 2025, placa = "ABC1D23", apelido = "Carro Guilherme"))
}