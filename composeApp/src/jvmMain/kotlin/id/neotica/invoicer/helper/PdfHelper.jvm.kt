package id.neotica.invoicer.helper

import androidx.compose.runtime.Composable
import androidx.compose.ui.ImageComposeScene
import androidx.compose.ui.graphics.toAwtImage
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory
import org.jetbrains.skia.Image
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

fun renderComposableToBitmapDesktop(composable: @Composable () -> Unit, size: IntSize): BufferedImage {
    val scene = ImageComposeScene(
        density = Density(1f),
        width = size.width,
        height = size.height
    )
    scene.setContent { composable() }
    val imageBitmap: Image = scene.render(0L)
    scene.close()
    return imageBitmap.toComposeImageBitmap().toAwtImage()
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