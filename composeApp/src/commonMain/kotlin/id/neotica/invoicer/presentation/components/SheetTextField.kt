package id.neotica.invoicer.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.neotica.invoicer.presentation.theme.NeoColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SheetTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    BasicTextField(
        modifier =
            modifier
                .border(
                    width = 1.dp,
                    color = NeoColor.black,
                    shape = RoundedCornerShape(2.dp)
                )
                .padding(4.dp)
                .fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
    )
}