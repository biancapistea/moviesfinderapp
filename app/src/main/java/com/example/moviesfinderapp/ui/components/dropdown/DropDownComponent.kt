package com.example.moviesfinderapp.ui.components.dropdown

import androidx.compose.foundation.background
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.movies.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropDownComponent(
    options: List<String>,
    selectedOptionText: String,
    onValueChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = Modifier.background(Color.DarkGray),
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            readOnly = true,
            value = selectedOptionText,
            onValueChange = { },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                textColor = Color.White,
                leadingIconColor = colorResource(
                    id = R.color.green_chips
                ),
                trailingIconColor = colorResource(
                    id = R.color.green_chips
                ),
                focusedTrailingIconColor = colorResource(
                    id = R.color.green_chips
                ),
                unfocusedIndicatorColor = colorResource(
                    id = R.color.green_chips
                ),
                focusedIndicatorColor = colorResource(
                    id = R.color.green_chips
                ),
            )
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        onValueChange(selectionOption)
                        expanded = false
                    }
                ) {
                    Text(text = selectionOption)
                }
            }
        }
    }
}