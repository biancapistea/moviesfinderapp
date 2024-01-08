package com.example.moviesfinderapp.ui.components.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.movies.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarSection(
    keywordTexted: String,
    onSearchKeywordChanged: (String) -> Unit,
    openSearchBar: Boolean,
    onSearchPressed: () -> Unit,
    setOpenSearchBar: (Boolean) -> Unit,
    placeHolderText: String = "Search movie based on keyword"
) {
    SearchBar(
        query = keywordTexted,
        onQueryChange = {
            onSearchKeywordChanged(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp),
        colors = SearchBarDefaults.colors(
            containerColor = Color.Black,
            inputFieldColors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.DarkGray,
                unfocusedTextColor = Color.White,
                unfocusedLabelColor = Color.White,
                unfocusedSupportingTextColor = Color.White,
                unfocusedPlaceholderColor = Color.White,
                errorContainerColor = Color.DarkGray,
                errorTextColor = Color.Red,
                errorLabelColor = Color.Red,
                cursorColor = Color.White,
                focusedIndicatorColor = Color.White,
                focusedLabelColor = Color.White,
                focusedSupportingTextColor = Color.White,
                focusedTextColor = Color.White
            )
        ),
        trailingIcon = {
            if (openSearchBar) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.White,
                    modifier = Modifier
                        .size(26.dp)
                        .clickable {
                            if (keywordTexted.isNotEmpty()) {
                                onSearchKeywordChanged("")
                            } else {
                                setOpenSearchBar(false)
                            }
                        },
                )
            }
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Search",
                tint = Color.White,
                modifier = Modifier
                    .size(20.dp),
            )
        },
        onSearch = {
            setOpenSearchBar(false)
            onSearchPressed()
        },
        active = openSearchBar,
        placeholder = { Text(text = placeHolderText) },
        onActiveChange = {
            setOpenSearchBar(it)
        })
    {}
}