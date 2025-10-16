package id.neotica.invoicer.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object MainScreen: Screen()
    @Serializable
    data object InvoiceScreen: Screen()
    @Serializable
    data object InputInvoiceScreen: Screen()
    @Serializable
    data object PreviewInvoiceView: Screen()
}