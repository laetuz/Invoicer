package id.neotica.invoicer.model

val martinData = InvoiceForm(
    issuer = "Ryo Martin Sopian",
    invoiceTitle = "Invoice DEMO",
    invoiceNumber = 1,
    billTo = "Customer 1",
    date = "18 October 2025",
    dueDate = "18 October 2025"
)

val itemDummy1 = Item(
    name = "Ayam Bakar",
    quantity = 3,
    rate = 25000,
)

val itemDummy2 = Item(
    name = "Ayam Goreng",
    quantity = 2,
    rate = 20000,
)

val itemDummy3 = Item(
    name = "Ayam Goreng with kecap and no saos sambal",
    quantity = 5,
    rate = 18000,
)