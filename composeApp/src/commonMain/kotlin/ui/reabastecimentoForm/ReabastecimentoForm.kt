import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import data.models.Reabastecimento
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.theme.AutomoramaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReabastecimentoForm(
    reabastecimento: Reabastecimento = Reabastecimento(),
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text("Reabastecimento",
                         maxLines = 1,
                         overflow = TextOverflow.Ellipsis
                    )
                        },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
                    }
                                 },
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    titleContentColor = MaterialTheme.colorScheme.primary),
                actions = {
                    OutlinedButton(
                        onClick = {
                        },
                        enabled = true,
                        shape = RoundedCornerShape(50.dp)

                    ) {
                        Text("Salvar")
                    }
                          },

                )


        }){ innerPadding ->
            Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
            ) {
                ElevatedCard(
                    shape = MaterialTheme.shapes.medium,
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    ),
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .fillMaxSize(fraction = 0.9f),
                    
                ) {
                    Column {
                        TextField(
                            value = "",
                            onValueChange = { },
                            label = { Text("Combustível") }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
            
                        // Same for other fields...
            
                        TextField(
                            value = "",
                            onValueChange = { },
                            label = { Text("Quilometragem Anterior") }
                        )
                    }
                }
            }
    }
    
    
}

@Preview
@Composable
fun ReabastecimentoFormPreview(){
    AutomoramaTheme {
        ReabastecimentoForm()
    }
}