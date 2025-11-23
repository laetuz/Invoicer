package id.neotica.invoicer.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.neotica.invoicer.maker.toFormattedDate
import id.neotica.invoicer.presentation.theme.NeoColor
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun NeoDatePicker(
    label: String,
    selectedDate: String,
    onDateSelected: (String) -> Unit
) {
    var isDatePickerOpen by remember { mutableStateOf(false) }

    // This is the main state for the DatePicker
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = kotlin.time.Clock.System.now().toEpochMilliseconds())

    // Read-only text field that shows the selected date
    BasicTextField(
        value = selectedDate,
        onValueChange = {}, // Not editable by typing
        readOnly = true,
//        label = { Text(label) },
        decorationBox = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("$label: ")
                it()
                Text(
                    text = "📅",
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .border(
                            width = 1.dp,
                            color = NeoColor.black
                        )
                        .clickable {
                            isDatePickerOpen = true // Open the dialog on icon click
                        }
                        .padding(4.dp)
                )
            }
        },
        modifier = Modifier

            .border(
                width = 1.dp,
                color = NeoColor.black
            )
            .padding(4.dp)
            .clickable {
            isDatePickerOpen = true
        }
    )

    // This is the actual DatePickerDialog
    if (isDatePickerOpen) {
        DatePickerDialog(
            onDismissRequest = {
                isDatePickerOpen = false // Close when user clicks outside
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        isDatePickerOpen = false // Close on "OK"

                        // Get the selected date and format it
                        val selectedMillis = datePickerState.selectedDateMillis
                        if (selectedMillis != null) {
                            onDateSelected(selectedMillis.toFormattedDate())
                        }
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        isDatePickerOpen = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(
                state = datePickerState
            )
        }
    }
}