package id.neotica.invoicer.helper

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntSize

expect class PlatformContextGetter

expect fun renderComposableToBitmap(
    composable: @Composable () -> Unit,
    size: IntSize
)