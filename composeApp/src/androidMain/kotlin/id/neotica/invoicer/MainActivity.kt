package id.neotica.invoicer

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.tooling.preview.Preview
import id.neotica.invoicer.helper.KoinDynamicContext

val localContext = staticCompositionLocalOf<Context> { error("No LocalContext provided") }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)


        setContent {
            CompositionLocalProvider(localContext provides this) {

                KoinDynamicContext(context = this) { App() }
            }

        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}