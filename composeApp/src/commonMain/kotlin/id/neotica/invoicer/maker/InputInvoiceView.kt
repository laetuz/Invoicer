package id.neotica.invoicer.maker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import id.neotica.invoicer.presentation.theme.NeoColor
import id.neotica.toast.ToastManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun InputInvoiceScreen() {

    var model by remember { mutableStateOf(
        InvoiceForm(
            issuer = "",
            invoiceTitle = "",
            invoiceNumber = 0,
            billTo = "",
            date = "",
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
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Bill to: ${model.billTo}",
                        modifier = Modifier.alpha(0.5f)
                    )
                    SimpleDropdownMenu("Bill to", customerList) {
                        model = model.copy(billTo = it)
                    }
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Row {
                        Text(
                            text = "Date: ",
                            modifier = Modifier.alpha(0.5f)
                        )
                        Text(model.date)
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

            ItemRowComponent(
                rowOne = {
                    itemList.forEach {
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
                                        .background(NeoColor.red)
                                        .padding(horizontal = 8.dp)
                                )
                                Text(it.name)

                            }
                            Spacer(Modifier.padding(2.dp))
                        }
                    }
                },
                rowTwo = {
                    itemList.forEach {
                        Text(it.quantity.toString())
                        Spacer(Modifier.padding(2.dp))
                    }
                },
                rowThree = {
                    itemList.forEach {
                        Text(it.rate.currencyType(currencyType))
                        Spacer(Modifier.padding(2.dp))
                    }
                },
                rowFour = {
                    itemList.forEach {
                        Text((it.quantity * it.rate).currencyType(currencyType))
                        Spacer(Modifier.padding(2.dp))
                    }
                }
            )

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
//                        OutlinedTextField(
//                            value = "Item",
//                            onValueChange = { itemMock.copy(amo = it.toLong()) },
//                        )
                    }
                )

                Button(
                    onClick = {
                        addItemState = !addItemState

                        if (itemMock != Item(
                                name = "",
                                quantity = 0,
                                rate = 0,
                        )) itemList.add(itemMock)
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
                        isRendering = false
                    }
                }){ Text("Save") }

            Text(renderingText)
        }
    }
}

@Composable
fun SimpleDropdownMenu(label: String, items: List<String>, selected: (String) -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }
//    val options = listOf("Option 1", "Option 2", "Option 3", "Option 4")
    var selectedOption by remember { mutableStateOf("") }
    selected(selectedOption)

    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopStart)
            .padding(end = 16.dp)
            .width(300.dp)
    ) {
        // 1. The Anchor: The button the user clicks
        OutlinedTextField(
            value = selectedOption,
            onValueChange = { selectedOption = it },
            label = { Text(label) },
            trailingIcon = {
                Text(
                    text = "down",
                    modifier = Modifier.clickable {
                        isExpanded = true
                    }
                )
//                Icon(
//                    imageVector = vectorResource(Res.drawable.compose_multiplatform),
//                    contentDescription = "Open menu",
//                    modifier = Modifier.clickable {
//                        isExpanded = true
//                    }
//                )
            }
        )

        // 2. The Dropdown Menu
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            // 3. The Menu Items
            items.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selectedOption = option
                        isExpanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun ItemRowComponent(
    rowOne: @Composable () -> Unit,
    rowTwo: @Composable () -> Unit,
    rowThree: @Composable () -> Unit,
    rowFour: @Composable () -> Unit,
) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Column(
            Modifier.weight(2f)
        ) {
            rowOne()
        }
        Column(
            Modifier.weight(1f)
        ) { rowTwo() }
        Column(
            Modifier.weight(1f)
        ) { rowThree() }
        Column(
            Modifier.weight(1f)
        ) { rowFour() }
    }
}