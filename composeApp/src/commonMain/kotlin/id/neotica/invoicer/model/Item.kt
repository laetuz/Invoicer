package id.neotica.invoicer.model

data class Item(
    val name: String,
    val quantity: Int,
    val rate: Long,
)
