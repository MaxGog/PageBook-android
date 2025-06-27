package ru.maxgog.pagebook.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.VerticalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import ru.maxgog.pagebook.R
import ru.maxgog.pagebook.models.EventModel
import ru.maxgog.pagebook.toKotlinxLocalDate
import ru.maxgog.pagebook.ui.dialogs.AddEventDialog
import ru.maxgog.pagebook.ui.items.EventItem
import ru.maxgog.pagebook.viewmodels.CalendarViewModel
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues()
) {
    val context = LocalContext.current
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(12) }
    val endMonth = remember { currentMonth.plusMonths(12) }
    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth
    )

    val showDialog by viewModel.showDialog.collectAsState()
    val dialogDate by viewModel.dialogDate.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    val selectedDate by viewModel.selectedDate.collectAsState()
    val events by viewModel.events.collectAsState()

    LaunchedEffect(selectedDate) {
        state.animateScrollToMonth(YearMonth.of(selectedDate.year, selectedDate.month))
    }

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { viewModel.showAddEventDialog(selectedDate) },
                modifier = Modifier.padding(contentPadding),
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_event)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(R.string.add_event))
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            VerticalCalendar(
                state = state,
                modifier = Modifier.weight(1f),
                contentPadding = contentPadding,
                dayContent = { day ->
                    val dayEvents = remember(day.date, events) {
                        events.filter { it.date == day.date }
                    }

                    Day(
                        day = day,
                        isSelected = day.date == selectedDate,
                        onClick = {
                            viewModel.onDateSelected(day.date.toKotlinxLocalDate())
                        },
                        events = dayEvents
                    )
                },
                monthHeader = { month ->
                    MonthHeader(month.yearMonth)
                }
            )

            val selectedEvents = remember(selectedDate, events) {
                events.filter { it.date == selectedDate }
            }

            EventsList(
                events = selectedEvents,
                onDelete = viewModel::deleteEvent,
                modifier = Modifier.weight(1f)
            )
        }
    }

    if (showDialog) {
        AddEventDialog(
            date = dialogDate,
            onDismiss = { viewModel.hideAddEventDialog() },
            onConfirm = { title, description, date, time ->
                viewModel.addEvent(
                    EventModel(
                        title = title,
                        description = description,
                        date = date,
                        time = time
                    )
                )
            }
        )
    }
}

@Composable
private fun EventsList(
    events: List<EventModel>,
    onDelete: (EventModel) -> Unit,
    modifier: Modifier = Modifier
) {
    if (events.isNotEmpty()) {
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(events, key = { it.id }) { event ->
                EventItem(
                    event = event,
                    onDelete = { onDelete(event) }
                )
            }
        }
    } else {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.no_events),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun MonthHeader(yearMonth: YearMonth) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Text(
            text = yearMonth.month.getDisplayName(
                TextStyle.FULL_STANDALONE,
                Locale.getDefault()
            ).replaceFirstChar { it.titlecase() } + " " + yearMonth.year,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp))
    }
}

@Composable
private fun Day(
    day: CalendarDay,
    isSelected: Boolean,
    onClick: () -> Unit,
    events: List<EventModel>
) {
    val containerColor = if (isSelected) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.surface
    }

    val contentColor = if (isSelected) {
        MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(4.dp)
            .clip(CircleShape)
            .background(containerColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = day.date.dayOfMonth.toString(),
                color = contentColor,
                style = MaterialTheme.typography.bodyMedium
            )

            if (events.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                )
            }
        }
    }
}