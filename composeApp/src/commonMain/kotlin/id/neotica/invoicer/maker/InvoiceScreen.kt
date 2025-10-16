package id.neotica.invoicer.maker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.neotica.invoicer.model.InvoiceForm
import id.neotica.invoicer.model.itemDummy1
import id.neotica.invoicer.model.itemDummy2

@Composable
fun InvoiceScreen(model: InvoiceForm) {
    LazyColumn(
        Modifier
            .padding(16.dp)
    ) {
        item {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(model.issuer)
                Column {
                    Text(model.invoiceTitle)
                    Text(model.invoiceNumber)
                }
            }
            Spacer(Modifier.padding(8.dp))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(model.billTo)
                Column {
                    Text(model.date)
                    Text(model.dueDate)
                }
            }
            val itemList = listOf(
                itemDummy1, itemDummy2, itemDummy1
            )
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column {
                    Text("Item")
                    itemList.forEach {
                        Text(it.name)
                    }
                }
                Column {
                    Text("Quantity")
                    itemList.forEach {
                        Text(it.quantity.toString())
                    }
                }
                Column {
                    Text("Rate")
                    itemList.forEach {
                        Text(it.rate.toString())
                    }
                }
                Column {
                    Text("Amount")
                    itemList.forEach {
                        Text((it.quantity * it.rate).toString())
                    }
                }
            }
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total")
                Text(itemList.sumOf { it.quantity * it.rate }.toString())
            }
        }
    }
}