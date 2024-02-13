import ViewModels.VeiculoFormViewModel
import ViewModels.VeiculoViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import app.cash.sqldelight.db.SqlDriver
import data.models.Veiculo
import data.repositories.VeiculoRepository
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.VeiculoForm.VeiculoForm
import ui.Veiculos.VeiculosListScreen
import ui.Veiculos.VeiculosListUiState

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App(sqlDriver: SqlDriver) {

    PreComposeApp {
        val veiculoRepository = VeiculoRepository(sqlDriver)
        val navigator = rememberNavigator()
        NavHost(
            navigator = navigator,
            initialRoute = "veiculosList",
            navTransition = NavTransition()
        ){
            scene(
                "veiculosList",
                navTransition = NavTransition()
            ){
                val viewModel = remember {
                    VeiculoViewModel(veiculoRepository)
                }
                val uiState by viewModel.uiState.collectAsState(VeiculosListUiState())
                VeiculosListScreen(
                    uiState = uiState,
                    onNewVeiculoClick = {
                        navigator.navigate("veiculoForm")
                    },
                    onVeiculoClick = {
                        navigator.navigate("veiculoForm/${it.id}")

                    },
                    onDeleteClick = {
                       veiculoRepository.deleteVeiculo(it)

                    }

                )
            }
            scene(
                "veiculoForm/{id}?",
                navTransition = NavTransition()
            ) {
                val id: Long? = it.path<Long>("id")
                val viewModel = remember {
                    VeiculoFormViewModel(veiculoRepository, id)
                }
                val uiState by viewModel.uiState.collectAsState()
                VeiculoForm(
                    uiState = uiState,
                    onSaveClick = {
                        viewModel.save()
                        navigator.goBack()
                    }
                )
            }
        }
    }
}

