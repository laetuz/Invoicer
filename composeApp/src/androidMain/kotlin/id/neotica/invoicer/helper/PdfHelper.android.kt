package id.neotica.invoicer.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.pdf.PdfDocument
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.IntSize
import id.neotica.invoicer.RenderStuff
import org.koin.core.qualifier.named
import org.koin.mp.KoinPlatformTools
import java.io.File
import java.io.FileOutputStream

actual fun renderComposableToBitmap(
    composable: @Composable (() -> Unit),
    size: IntSize,

) {
//
//    renderComposableToBitmapAndroid(composable, size)
    val koinContext = KoinPlatformTools.defaultContext().get().get<Context>(named("context"))

    RenderShit().renderComposable(
        context = koinContext,
        composable = composable,
        size = size
    )

}

class RenderShit(): RenderStuff {
    override fun renderComposable(
        context: Context,
        composable: @Composable (() -> Unit),
        size: IntSize
    ): Bitmap {
        val composeView = ComposeView(context).apply {
            setContent { composable() }
            measure(
                View.MeasureSpec.makeMeasureSpec(size.width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(size.height, View.MeasureSpec.EXACTLY)
            )
            layout(0, 0, size.width, size.height)
        }

        // Draw into a Bitmap
        val bitmap = Bitmap.createBitmap(size.width, size.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        composeView.draw(canvas)
        return bitmap
    }

}


fun renderComposableToBitmapAndroid(
    context: Context,
    composable: @Composable () -> Unit,
    size: IntSize
): Bitmap {
    // Create a ComposeView to render off-screen
    val composeView = ComposeView(context).apply {
        setContent { composable() }
        measure(
            View.MeasureSpec.makeMeasureSpec(size.width, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(size.height, View.MeasureSpec.EXACTLY)
        )
        layout(0, 0, size.width, size.height)
    }

    // Draw into a Bitmap
    val bitmap = Bitmap.createBitmap(size.width, size.height, Bitmap.Config.ARGB_8888)
    val canvas = android.graphics.Canvas(bitmap)
    composeView.draw(canvas)
    return bitmap
}

fun saveBitmapAsPdf(bitmap: Bitmap, file: File) {
    val document = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, 1).create()
    val page = document.startPage(pageInfo)
    val canvas = page.canvas
    canvas.drawBitmap(bitmap, 0f, 0f, null)
    document.finishPage(page)

    FileOutputStream(file).use { out ->
        document.writeTo(out)
    }
    document.close()
}

actual class PlatformContextGetter {

}