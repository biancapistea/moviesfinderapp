package com.example.moviesfinderapp.ui.components.sections

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movies.R
import com.example.moviesfinderapp.ui.components.buttons.ButtonComponent
import com.example.moviesfinderapp.ui.components.chips.MultipleSelectionChips
import com.example.moviesfinderapp.ui.components.dropdown.DropDownComponent
import com.example.moviesfinderapp.ui.components.slider.RangeSliderExample
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FilterSectionContent(
    genres: List<String>,
    onGenreItemSelected: (Int) -> Unit,
    checkIfGenreIsSelected: (Int) -> Boolean,
    sheetState: BottomSheetState,
    onSaveFiltersButtonClicked: () -> Unit,
    selectedYears: ClosedFloatingPointRange<Float>,
    selectedRating: ClosedFloatingPointRange<Float>,
    onSelectedYearChanged: (ClosedFloatingPointRange<Float>) -> Unit,
    onSelectedRatingChanged: (ClosedFloatingPointRange<Float>) -> Unit,
    onLanguageChanged: (String) -> Unit,
    selectedLanguage: String,
    languages: List<String>,
    onClearFiltersPressed: () -> Unit
) {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = LocalConfiguration.current.screenHeightDp.dp - 50.dp)
            .padding(16.dp)
            .background(Color.Black)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = Color.DarkGray,
                        shape = RoundedCornerShape(50),
                    )
                    .size(width = 36.dp, height = 4.dp),

                )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(12.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(color = colorResource(id = R.color.dark_grey))
        ) {
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Text(
                    modifier = Modifier.padding(top = 20.dp),
                    text = "Rating",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                RangeSliderExample(
                    valueRange = selectedRating,
                    onValueChange = onSelectedRatingChanged,
                    values = 0f..10f
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(12.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(color = colorResource(id = R.color.dark_grey))
        ) {
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)) {
                Text(
                    modifier = Modifier.padding(bottom = 20.dp),
                    text = "Genre",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                MultipleSelectionChips(
                    chips = genres,
                    onItemSelected = onGenreItemSelected,
                    checkIfSelected = checkIfGenreIsSelected
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(12.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(color = colorResource(id = R.color.dark_grey))
        ) {
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Text(
                    modifier = Modifier.padding(top = 20.dp, bottom = 20.dp),
                    text = "Release Years",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                RangeSliderExample(
                    valueRange = selectedYears,
                    onValueChange = onSelectedYearChanged,
                    values = 1900f..2023f
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(12.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(color = colorResource(id = R.color.dark_grey))
        ) {
            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)) {
                Text(
                    modifier = Modifier.padding(bottom = 20.dp),
                    text = "Original language",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                DropDownComponent(
                    options = languages,
                    selectedOptionText = selectedLanguage,
                    onValueChange = onLanguageChanged
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ButtonComponent(
                modifier = Modifier.weight(1f),
                text = "Clear filters", onClick = {
                    onClearFiltersPressed()
                    scope.launch {
                        sheetState.collapse()
                    }
                }, colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = colorResource(id = R.color.green_chips),
                    disabledContentColor = Color.White,
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                border = BorderStroke(1.dp, colorResource(id = R.color.green_chips))
            )
            ButtonComponent(
                modifier = Modifier.weight(1f),
                text = "Save changes",
                onClick = {
                    scope.launch {
                        onSaveFiltersButtonClicked()
                        sheetState.collapse()
                    }
                }
            )
        }
    }
}
