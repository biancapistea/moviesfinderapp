package com.example.moviesfinderapp.ui.components.chips

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.movies.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MultipleSelectionChips(
    modifier: Modifier = Modifier,
    chips: List<String>,
    onItemSelected: (Int) -> Unit = {},
    checkIfSelected: (Int) -> Boolean = { false },
) {

    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        chips.forEachIndexed { index, chipsItem ->
            Box(
                modifier = Modifier
                    .clickable {
                        onItemSelected(index)
                    }
                    .clip(RoundedCornerShape(30.dp))
                    .background(
                        if (checkIfSelected(index)) {
                            colorResource(id = R.color.green_chips)
                        } else Color.DarkGray
                    )
                    .padding(8.dp)
            ) {
                Text(
                    chipsItem,
                    modifier = Modifier
                        .padding(vertical = 3.dp, horizontal = 5.dp), color = Color.White
                )
            }
        }
    }
}
