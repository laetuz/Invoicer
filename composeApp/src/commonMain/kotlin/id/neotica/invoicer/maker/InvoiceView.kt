package id.neotica.invoicer.maker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.neotica.invoicer.helper.CURRENCY
import id.neotica.invoicer.helper.currencyType
import id.neotica.invoicer.model.InvoiceForm
import id.neotica.invoicer.model.Item
import id.neotica.invoicer.presentation.theme.NeoColor

@Composable
fun InvoiceScreen(model: InvoiceForm, items: List<Item>, currency: CURRENCY) {

    val currencyType = currency

    LazyColumn(
        Modifier
            .padding(16.dp)
    ) {
        item {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
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
                    if (model.invoiceNumber > 0) {
                        Text(
                            text = "#${model.invoiceNumber}",
                            modifier = Modifier.alpha(0.5f)
                        )
                    }
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
                    if (model.dueDate.isNotEmpty()) {
                        Row {
                            Text(
                                text = "Due Date: ",
                                modifier = Modifier.alpha(0.5f)
                            )
                            Text(model.dueDate)
                        }
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
            ItemRowComponent(
                rowOne = {
                    items.forEach {
                        Text(it.name)
                    }
                },
                rowTwo = {
                    items.forEach {
                        Text(it.quantity.toString())
                    }
                },
                rowThree = {
                    items.forEach {
                        Text(it.rate.currencyType(currencyType))
                    }
                },
                rowFour = {
                    items.forEach {
                        Text((it.quantity * it.rate).currencyType(currencyType))
                    }
                }
            )
            Row(
                Modifier.fillMaxWidth().padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Total: ",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = items.sumOf { it.quantity * it.rate }.currencyType(currencyType),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}