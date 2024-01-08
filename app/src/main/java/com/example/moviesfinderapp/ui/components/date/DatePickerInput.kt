package com.example.moviesfinderapp.ui.components.date

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CalendarLocale
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.movies.R
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePickerInput(
    dateTextFieldColors: TextFieldColors = TextFieldDefaults.colors(
        unfocusedContainerColor = Color.DarkGray,
        unfocusedTextColor = Color.White,
        unfocusedLabelColor = Color.White,
        unfocusedSupportingTextColor = Color.White,
        unfocusedPlaceholderColor = colorResource(id = R.color.green_chips),
        errorContainerColor = Color.DarkGray,
        errorTextColor = Color.Red,
        errorLabelColor = Color.Red,
        cursorColor = colorResource(id = R.color.green_chips),
        focusedIndicatorColor = colorResource(id = R.color.green_chips),
        focusedLabelColor = Color.White,
        focusedSupportingTextColor = colorResource(id = R.color.green_chips),
        focusedTextColor = colorResource(id = R.color.green_chips),
    ),
    datePickerState: DateRangePickerState,
) {
    DateRangePicker(
        modifier = Modifier.fillMaxWidth().padding(0.dp),
        title = null,
        headline = null,
        state = datePickerState,
        colors = DatePickerDefaults.colors(
            headlineContentColor = Color.White,
            titleContentColor = Color.White,
            containerColor = Color.White,
            selectedDayContainerColor = colorResource(id = R.color.green_chips),
            dateTextFieldColors = dateTextFieldColors
        ),
        showModeToggle = false
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDateTime.toMillis() = this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

