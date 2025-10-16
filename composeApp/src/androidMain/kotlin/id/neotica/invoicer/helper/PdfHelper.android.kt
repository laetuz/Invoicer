package id.neotica.invoicer.helper

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap

actual fun renderComposableToBitmap(
    composable: @Composable (() -> Unit),
    size: IntSize,
) {
//    renderComposableToBitmap(composable, size)
}

//fun renderComposableToBitmapAndroid(
//    composable: @Composable (() -> Unit),
//    size: IntSize
//): Bitmap {
//    val composeView = ComposeView(context).apply {
//        setContent {
//            Box(Modifier.size(size.width.dp, size.height.dp)) {
//                composable()
//            }
//        }
//        measure(
//            View.MeasureSpec.makeMeasureSpec(size.width, View.MeasureSpec.EXACTLY),
//            View.MeasureSpec.makeMeasureSpec(size.height, View.MeasureSpec.EXACTLY)
//        )
//        layout(0, 0, size.width, size.height)
//    }
//
//    val bitmap = createBitmap(size.width, size.height)
//    val canvas = Canvas(bitmap)
//    composeView.draw(canvas)
//    return bitmap
//}