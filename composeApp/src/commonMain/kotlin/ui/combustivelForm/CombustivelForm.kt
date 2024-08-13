import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.combustivelForm.CombustivelFormUIState
import ui.theme.AutomoramaTheme
import ui.topAppBar.TopAppBarSave

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CombustivelFormScreen(
    uiState: CombustivelFormUIState = CombustivelFormUIState(),
    onSaveClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier,
        topBar = {
            TopAppBarSave(
                title = uiState.topAppBarTitle,
                onSave = onSaveClick,
                onBack = onBackClick,
                isValid = uiState.isValid
            )
        },
        content = { innerPadding ->
            val nome = uiState.nome

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.padding(innerPadding)
                    .fillMaxSize()
            ) {
                ElevatedCard(
                    shape = MaterialTheme.shapes.medium,
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    ),
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .fillMaxSize(fraction = 0.95f),

                    ){

                    FlowColumn(
                        modifier = Modifier.padding(innerPadding).fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.Top)
                    ) {
                        OutlinedTextField(
                            value = nome,
                            onValueChange = uiState.onNomeChange,
                            label = {
                                Text("Nome")
                            },
                            isError = nome.isEmpty() || uiState.nameAlreadyExist,
                            supportingText = {
                                if (uiState.nameAlreadyExist) {
                                    Text(
                                        text = "Já existe um combustível com esse nome",
                                        color = MaterialTheme.colorScheme.error,
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                            }
                        )
                    }
                }
            }


        }
    )
}

@Preview
@Composable
fun CombustivelFormPreview() {
    AutomoramaTheme(true){
        CombustivelFormScreen()
    }
}
