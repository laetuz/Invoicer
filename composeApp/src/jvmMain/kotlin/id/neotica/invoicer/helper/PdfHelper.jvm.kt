package id.neotica.invoicer.helper

import androidx.compose.runtime.Composable
import androidx.compose.ui.ImageComposeScene
import androidx.compose.ui.graphics.toAwtImage
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory
import java.awt.image.BufferedImage
import java.io.File
import androidx.compose.ui.graphics.toComposeImageBitmap

actual fun renderComposableToBitmap(
    composable: @Composable (() -> Unit),
    size: IntSize
) {
    val bufferedImage = renderComposableToBitmapDesktop(composable, size)
    saveBufferedImageAsPdf(bufferedImage, File("output.pdf"))
}

fun renderComposableToBitmapDesktop(
    composable: @Composable () -> Unit,
    baseSize: IntSize, // The size at 1x scale (e.g., 595, 842 for A4)
    scaleFactor: Float = 4f // 4x scale gives ~300 DPI, great for printing
): BufferedImage {
    // Calculate the final pixel dimensions
    val finalSize = IntSize(
        width = (baseSize.width * scaleFactor).toInt(),
        height = (baseSize.height * scaleFactor).toInt()
    )

    val scene = ImageComposeScene(
        density = Density(scaleFactor), // Use the scale factor as the density!
        width = finalSize.width,
        height = finalSize.height
    )

    try {
        scene.setContent { composable() }
        val skiaImage: org.jetbrains.skia.Image = scene.render()
        return skiaImage.toComposeImageBitmap().toAwtImage()
    } finally {
        scene.close()
    }
}

fun saveBufferedImageAsPdf(image: BufferedImage, file: File) {
    val document = PDDocument()
    val page = PDPage()
    document.addPage(page)

    val pdImage = LosslessFactory.createFromImage(document, image)
    val contentStream = org.apache.pdfbox.pdmodel.PDPageContentStream(document, page)
    contentStream.drawImage(pdImage, 0f, 0f, page.mediaBox.width, page.mediaBox.height)
    contentStream.close()

    document.save(file)
    document.close()
}