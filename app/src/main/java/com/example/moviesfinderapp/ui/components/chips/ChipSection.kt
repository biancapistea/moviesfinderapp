package com.example.moviesfinderapp.ui.components.chips

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movies.R
import com.example.moviesfinderapp.ui.models.ChipModel

@Composable
fun ChipSection(
    chips: List<ChipModel>,
    setSelectedChip: (String) -> Unit
) {
    var selectedChipIndex by remember { mutableIntStateOf(0) }
    LazyRow {
        items(chips.size) { index ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(start = 15.dp, top = 15.dp, bottom = 15.dp)
                    .clickable {
                        selectedChipIndex = index
                        setSelectedChip(chips[index].title)
                        chips[index].onClick()
                    }
                    .clip(RoundedCornerShape(30.dp))
                    .background(
                        if (selectedChipIndex == index) {
                            colorResource(id = R.color.green_chips)
                        } else Color.DarkGray
                    )
                    .padding(15.dp)
            ) {
                Text(
                    text = chips[index].title,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(
                        Font(R.font.graphik_regular)
                    )
                )
            }
        }
    }
}