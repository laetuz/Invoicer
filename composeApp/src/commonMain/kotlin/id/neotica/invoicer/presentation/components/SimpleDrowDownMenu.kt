package id.neotica.invoicer.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SimpleDropdownMenu(
    label: String,
    items: List<String>,
    modifier: Modifier = Modifier,
    selected: (String) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
//    val options = listOf("Option 1", "Option 2", "Option 3", "Option 4")
    var selectedOption by remember { mutableStateOf("") }
    selected(selectedOption)

    Box(
        modifier = modifier
//            .wrapContentSize(Alignment.TopStart)
            .padding(end = 16.dp)
    ) {
        // 1. The Anchor: The button the user clicks
        BasicTextField(
            value = selectedOption,
            onValueChange = {
                selectedOption = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = androidx.compose.ui.graphics.Color.Black
                ),
            decorationBox = {
                Row(
                    modifier = Modifier.padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(Modifier.weight(1f).padding(end = 4.dp)) { it() }
                    Text(
                        text = "🔽",
                        modifier = Modifier.clickable {
                            isExpanded = true
                        }
                    )
                }
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