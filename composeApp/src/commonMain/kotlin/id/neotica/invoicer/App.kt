package id.neotica.invoicer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import id.neotica.invoicer.helper.renderComposableToBitmap
import id.neotica.invoicer.maker.InvoiceScreen
import id.neotica.invoicer.model.martinData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val scope = rememberCoroutineScope()

            var renderingText by remember { mutableStateOf("") }
            var isRendering by remember { mutableStateOf(false) }

            InvoiceScreen(martinData)
            Button(
                enabled = !isRendering,
                onClick = {
                scope.launch(Dispatchers.IO) {
                    renderingText = "Loading..."
                    isRendering = true

                    withContext(Dispatchers.IO) {
                        renderComposableToBitmap({ InvoiceScreen(martinData) }, IntSize(595, 842))
                    }

                    renderingText = "Done!"
                    isRendering = false
                }
            }){ Text("Save") }

            Text(renderingText)
        }
    }
}