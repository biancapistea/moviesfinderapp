package com.example.moviesfinderapp.ui.components.sections

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.moviesfinderapp.ui.screens.dashboard.MovieItem
import com.example.moviesfinderapp.ui.models.MovieUi

@ExperimentalFoundationApi
@Composable
fun MoviesSection(
    movies: List<MovieUi>,
    onLoadNextPage: () -> Unit,
    isLoading: Boolean,
    onMovieCLicked: (MovieUi) -> Unit,
    areMoviesLoading: Boolean
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val scrollState = rememberLazyGridState()
        val span: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(1) }
//        if (areMoviesLoading) {
//            Column(
//                Modifier
//                    .fillMaxSize()
//                    .padding(20.dp),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
//            ) {
//                CircularProgressIndicator(color = Color.White)
//            }
//        } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = scrollState,
            contentPadding = PaddingValues(start = 7.5.dp, end = 7.5.dp, top = 12.dp),
            modifier = Modifier.wrapContentHeight()
        ) {
            items(movies.size) {
                LaunchedEffect(key1 = scrollState) {
                    if (it >= movies.size - 1) {
                        onLoadNextPage()
                    }
                }
                MovieItem(
                    position = it, movieUi = movies[it], onClick = { position ->
                        onMovieCLicked(movies[position])
                    }
                )
            }
            item(span = span) {
                if (isLoading) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(20.dp), horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }
            }
        }
        //}
    }
}