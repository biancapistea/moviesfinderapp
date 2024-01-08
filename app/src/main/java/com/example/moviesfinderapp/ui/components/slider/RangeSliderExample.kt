package com.example.moviesfinderapp.ui.components.slider

import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.RangeSlider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import com.example.movies.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RangeSliderExample(
    valueRange: ClosedFloatingPointRange<Float>,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
    values: ClosedFloatingPointRange<Float>,
) {
    val steps = values.endInclusive.toInt() - values.start.toInt() - 1
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "${valueRange.start.toInt()} - ${valueRange.endInclusive.toInt()}",
            color = Color.White,
            fontSize = 16.sp
        )
        RangeSlider(
            value = valueRange,
            steps = steps,
            onValueChange = { range -> onValueChange(range) },
            valueRange = values,
            colors = SliderDefaults.colors(
                thumbColor = colorResource(id = R.color.green_chips),
                activeTrackColor = colorResource(id = R.color.green_chips),
                inactiveTrackColor = Color.Black,
                activeTickColor = colorResource(id = R.color.green_chips),
                inactiveTickColor = Color.Black
            )
        )
    }
}
