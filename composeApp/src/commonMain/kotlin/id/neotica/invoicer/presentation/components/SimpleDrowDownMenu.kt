package id.neotica.invoicer.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
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