package id.neotica.invoicer

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntSize
import org.koin.dsl.module

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun platformModule() = module {
    single {
//        createDataStoreAndroid(get())
    }
}

interface RenderStuff {
    fun renderComposable(
        context: Context,
        composable: @Composable () -> Unit,
        size: IntSize
    ): Bitmap
}

//fun contextModule(context: Context) = module {
//    single<Context> {  }
//}

fun contexttt(context: Context) = module {
    single { context.applicationContext }
}
