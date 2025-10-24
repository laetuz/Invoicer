package id.neotica.invoicer.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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