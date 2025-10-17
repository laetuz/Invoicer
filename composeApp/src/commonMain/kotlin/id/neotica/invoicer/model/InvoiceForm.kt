package id.neotica.invoicer.model

data class InvoiceForm(
    val issuer: String,
    val invoiceTitle: String,
    val invoiceNumber: Long,
    val billTo: String,
    val date: String,
    val dueDate: String
)
