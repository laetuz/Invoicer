package id.neotica.invoicer.maker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
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

    LazyColumn(
        Modifier
            .padding(16.dp)
    ) {
        item {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = model.issuer,
                    onValueChange = { model = model.copy(issuer = it) },
                    label = { Text("Issuer") }
                )
                Text(
                    text = model.issuer,
                    fontWeight = FontWeight.Bold
                )
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
                    Text(
                        text = model.billTo,
                        fontWeight = FontWeight.Bold
                    )
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