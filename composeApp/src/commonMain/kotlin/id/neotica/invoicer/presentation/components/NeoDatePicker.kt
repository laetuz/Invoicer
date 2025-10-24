package id.neotica.invoicer.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import id.neotica.invoicer.maker.toFormattedDate
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
    OutlinedTextField(
        value = selectedDate,
        onValueChange = {}, // Not editable by typing
        readOnly = true,
        label = { Text(label) },
        trailingIcon = {
            Text(
                text = "Select",
                modifier = Modifier.clickable {
                    isDatePickerOpen = true // Open the dialog on icon click
                }
            )
        },
        modifier = Modifier.clickable {
            isDatePickerOpen = true // Open the dialog on text field click
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