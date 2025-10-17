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
import androidx.compose.ui.unit.sp
import id.neotica.invoicer.helper.CURRENCY
import id.neotica.invoicer.helper.currencyType
import id.neotica.invoicer.helper.renderComposableToBitmap
import id.neotica.invoicer.model.itemDummy1
import id.neotica.invoicer.model.itemDummy2
import id.neotica.invoicer.model.itemDummy3
import id.neotica.invoicer.model.martinData
import id.neotica.invoicer.presentation.theme.NeoColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun InputInvoiceScreen() {

    var model by remember { mutableStateOf(martinData) }
    val currencyType = CURRENCY.IDR

    val scope = rememberCoroutineScope()
    var renderingText by remember { mutableStateOf("") }
    var isRendering by remember { mutableStateOf(false) }

    val issuerList = listOf("Martin", "Martha", "Molly")
    val customerList = listOf("Paulus", "Budi", "Kobe")

    LazyColumn(
        Modifier
            .padding(16.dp)
    ) {
        item {

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SimpleDropdownMenu("Issuer", issuerList) {
                    model = model.copy(issuer = it)
                }
                Column {
                    Text(
                        text = model.invoiceTitle,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
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
                        text = "Bill to:",
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
            val itemList = listOf(
                itemDummy1, itemDummy2, itemDummy1, itemDummy3
            )
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
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Column(
                    Modifier.weight(2f)
                ) {
                    itemList.forEach {
                        Text(it.name)
                    }
                }
                Column(
                    Modifier.weight(1f)
                ) {
                    itemList.forEach {
                        Text(it.quantity.toString())
                    }
                }
                Column(
                    Modifier.weight(1f)
                ) {
                    itemList.forEach {
                        Text(it.rate.currencyType(currencyType))
                    }
                }
                Column(
                    Modifier.weight(1f)
                ) {
                    itemList.forEach {
                        Text((it.quantity * it.rate).currencyType(currencyType))
                    }
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
                            renderComposableToBitmap({ InvoiceScreen(model, currencyType) }, IntSize(595, 842))
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