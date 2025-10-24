package id.neotica.invoicer.maker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import id.neotica.invoicer.helper.CURRENCY
import id.neotica.invoicer.helper.currencyType
import id.neotica.invoicer.helper.renderComposableToBitmap
import id.neotica.invoicer.model.InvoiceForm
import id.neotica.invoicer.model.Item
import id.neotica.invoicer.model.itemDummy1
import id.neotica.invoicer.model.itemDummy2
import id.neotica.invoicer.model.itemDummy3
import id.neotica.invoicer.presentation.components.ItemRowComponent
import id.neotica.invoicer.presentation.components.NeoDatePicker
import id.neotica.invoicer.presentation.components.SimpleDropdownMenu
import id.neotica.invoicer.presentation.theme.NeoColor
import id.neotica.toast.ToastManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun InputInvoiceScreen() {
    val todayDate = Clock.System.now().toEpochMilliseconds().toFormattedDate()

    var model by remember { mutableStateOf(
        InvoiceForm(
            issuer = "",
            invoiceTitle = "",
            invoiceNumber = 0,
            billTo = "",
            date = todayDate,
            dueDate = ""
        )
    ) }
    val currencyType = CURRENCY.IDR

    val scope = rememberCoroutineScope()
    val toastManager by remember { mutableStateOf(ToastManager()) }
    var renderingText by remember { mutableStateOf("") }
    var isRendering by remember { mutableStateOf(false) }

    val issuerList = listOf("Martin", "Martha", "Molly")
    val customerList = listOf("Paulus", "Budi", "Kobe")
    val itemList = remember {
        mutableStateListOf(
            itemDummy1, itemDummy2, itemDummy1, itemDummy3
        )
    }

    LazyColumn(
        Modifier
            .padding(16.dp)
    ) {
        item {

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    SimpleDropdownMenu("Issuer", issuerList) {
                        model = model.copy(issuer = it)
                    }
                    Text(
                        text = model.issuer,
                        modifier = Modifier.alpha(0.5f)
                    )
                    Column {
                        Text(
                            text = "Bill to: ${model.billTo}",
                            modifier = Modifier.alpha(0.5f)
                        )
                        SimpleDropdownMenu("Bill to", customerList) {
                            model = model.copy(billTo = it)
                        }
                    }
                }

                Column {
                    OutlinedTextField(
                        value = model.invoiceTitle,
                        onValueChange = {
                            model = model.copy(invoiceTitle =
                                it.replaceFirstChar { char ->
                                    if (char.isLowerCase()) char.titlecase() else char.toString()
                                }
                            )
                        },
                        label = { Text("Title") }
                    )
                    Text(
                        text = model.invoiceTitle,
                        modifier = Modifier.alpha(0.5f)
                    )
                    OutlinedTextField(
                        value = if (model.invoiceNumber == 0L) "" else model.invoiceNumber.toString(),
                        onValueChange = {
                            val invoiceNumber = it.toLongOrNull() ?: 0
                            model = model.copy(invoiceNumber = invoiceNumber)
                        },
                        label = { Text("Invoice Number") }
                    )
                    Text(
                        text = "#${model.invoiceNumber}",
                        modifier = Modifier.alpha(0.5f)
                    )
                }
            }
            Spacer(Modifier.padding(8.dp))

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    NeoDatePicker(
                        label = "Date",
                        selectedDate = model.date,
                    ) {
                        model = model.copy(date = it)
                    }
                    Row {
                        Text(
                            text = "Date: ",
                            modifier = Modifier.alpha(0.5f)
                        )
                        Text(model.date)
                    }
                }
                Column {
                    NeoDatePicker(
                        label = "Due Date",
                        selectedDate = model.dueDate,
                    ) {
                        model = model.copy(dueDate = it)
                    }
                    Row {
                        Text(
                            text = "Due Date: ",
                            modifier = Modifier.alpha(0.5f)
                        )
                        Text(model.dueDate)
                    }
                }



            }
            Row(
                modifier = Modifier.fillMaxWidth().background(NeoColor.black),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Column(
                    Modifier.weight(2f)
                ) {
                    Text(
                        text = "Item",
                        color = NeoColor.white,
                    )
                }
                Column(
                    Modifier.weight(1f)
                ) {
                    Text(
                        text = "Quantity",
                        color = NeoColor.white
                    )
                }
                Column(
                    Modifier.weight(1f)
                ) {
                    Text(
                        text = "Rate",
                        color = NeoColor.white,
                    )
                }
                Column(
                    Modifier.weight(1f)
                ) {
                    Text(
                        text = "Amount",
                        color = NeoColor.white
                    )
                }
            }

            Spacer(Modifier.padding(2.dp))

            itemList.forEach {
                ItemRowComponent(
                    rowOne = {
                        Column {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                ->
                                Text(
                                    text = "Delete",
                                    color = NeoColor.white,
                                    modifier = Modifier
                                        .clickable {
                                            itemList.remove(it)
                                        }
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(NeoColor.red)
                                        .padding(horizontal = 8.dp)
                                )
                                Text(it.name)
                            }
                        }
                    },
                    rowTwo = {
                        Text(it.quantity.toString())
                    },
                    rowThree = {
                        Text(it.rate.currencyType(currencyType))
                    },
                    rowFour= {
                        Text((it.quantity * it.rate).currencyType(currencyType))
                    },
                )
                HorizontalDivider(Modifier.padding(2.dp).alpha(0.2f), DividerDefaults.Thickness, NeoColor.black)
            }

            var addItemState by remember { mutableStateOf(false) }
            var itemMock by remember { mutableStateOf(Item(
                name = "",
                quantity = 0,
                rate = 0,
            )) }

            if (addItemState) {
                ItemRowComponent(
                    rowOne = {
                        OutlinedTextField(
                            value = itemMock.name,
                            onValueChange = {
                                itemMock = itemMock.copy(name = it)
                            },
                        )
                    },
                    rowTwo = {
                        OutlinedTextField(
                            value = if (itemMock.quantity == 0) "" else itemMock.quantity.toString(),
                            onValueChange = {
                                val qty = it.toIntOrNull() ?: 0
                                itemMock = itemMock.copy(quantity = qty)
                            },
                        )
                    },
                    rowThree = {
                        OutlinedTextField(
                            value = if (itemMock.rate == 0L) "" else itemMock.rate.toString(),
                            onValueChange = {
                                val rate = it.toLongOrNull() ?: 0
                                itemMock = itemMock.copy(rate = rate)
                            },
                        )
                    },
                    rowFour = {
                        Button({ addItemState = false }) { Text("Cancel") }
                    }
                )

                Button(
                    onClick = {
                        when {
                            itemMock.name.isBlank() -> toastManager.showToast("Name is empty")
                            itemMock.rate == 0L -> toastManager.showToast("Rate is empty")
                            itemMock.quantity == 0 -> toastManager.showToast("Quantity is empty")
                            itemMock.name.isNotBlank() && itemMock.rate != 0L && itemMock.quantity != 0 -> {
                                itemList.add(itemMock)
                                toastManager.showToast("Item added")
                                addItemState = !addItemState
                            }
                            else -> toastManager.showToast("Item is empty")
                        }

                    }
                ) {
                    Text("Add item")
                }

                Text(addItemState.toString())
            } else {
                Button(
                    onClick = {
                        addItemState = !addItemState
                        itemMock = Item(
                            name = "",
                            quantity = 0,
                            rate = 0,
                        )
                    }
                ) {
                    Text("Add item")
                }
            }

            Row(
                Modifier.fillMaxWidth().padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Total: ",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = itemList.sumOf { it.quantity * it.rate }.currencyType(currencyType),
                    fontWeight = FontWeight.Bold
                )
            }


            Button(
                enabled = !isRendering,
                onClick = {
                    scope.launch(Dispatchers.IO) {
                        renderingText = "Loading..."
                        isRendering = true

                        withContext(Dispatchers.IO) {
                            model = model.copy(invoiceNumber = model.invoiceNumber + 1)

                            renderComposableToBitmap({ InvoiceScreen(model, itemList, currencyType) }, IntSize(595, 842))
                        }

                        renderingText = "Done!"
                        toastManager.showToast("Done!")
                        isRendering = false
                    }
                }){ Text("Save") }

            Text(renderingText)


        }
    }
}

fun String.toLongDate(): String {
    val instant = this.toLongOrNull() ?: 0L
    val dateFormatted = Date(instant)

    val localFormatter = SimpleDateFormat("dd MMMM yyyy", Locale.US)
    return localFormatter.format(dateFormatted)
}

fun Long.toFormattedDate(): String {
    val instant = this
    val dateFormatted = Date(instant)

    val localFormatter = SimpleDateFormat("dd MMMM yyyy", Locale.US)
    return localFormatter.format(dateFormatted)
}