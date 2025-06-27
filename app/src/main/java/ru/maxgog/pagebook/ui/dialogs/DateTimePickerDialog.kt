package ru.maxgog.pagebook.ui.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import ru.maxgog.pagebook.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: (LocalDate, LocalTime) -> Unit,
    modifier: Modifier = Modifier
) {
    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        modifier = modifier,
        confirmButton = {
            FilledTonalButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val date = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        val time = LocalTime.of(timePickerState.hour, timePickerState.minute)
                        onConfirm(date, time)
                    }
                },
                enabled = datePickerState.selectedDateMillis != null,
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(stringResource(R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            DatePicker(state = datePickerState)
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
            Spacer(modifier = Modifier.height(16.dp))
            TimePicker(state = timePickerState)
        }
    }
}