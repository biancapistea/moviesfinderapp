package com.example.moviesfinderapp.ui.screens.dashboard

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.movies.R
import com.example.moviesfinderapp.ui.components.chips.ChipSection
import com.example.moviesfinderapp.ui.components.rating.RatingStar
import com.example.moviesfinderapp.ui.components.search.SearchBarSection
import com.example.moviesfinderapp.ui.components.sections.FilterSectionContent
import com.example.moviesfinderapp.ui.components.sections.MoviesSection
import com.example.moviesfinderapp.ui.components.sections.WatchNowSection
import com.example.moviesfinderapp.ui.models.MovieUi
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel, onMovieCLicked: (MovieUi) -> Unit
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    HomeScreenContent(
        uiState = uiState,
        onLoadNextPage = homeViewModel::loadNextPage,
        onGenreItemSelected = homeViewModel::onGenreItemSelected,
        checkIfGenreIsSelected = homeViewModel::checkIfGenreIsSelected,
        onSaveFiltersButtonClicked = homeViewModel::onFiltersSelected,
        setSelectedChip = homeViewModel::setSelectedChip,
        onMovieCLicked = onMovieCLicked,
        onSearchKeywordChanged = homeViewModel::onSearchKeywordChanged,
        onSearchPressed = homeViewModel::onSearchPressed,
        setOpenSearchBar = homeViewModel::onSearchBarOpened,
        onSelectedYearChanged = homeViewModel::onSelectedYearsChanged,
        onSelectedRatingChanged = homeViewModel::onSelectedRatingChanged,
        onLanguageChanged = homeViewModel::onLanguageChanged,
        onClearFiltersPressed = homeViewModel::resetFilters
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreenContent(
    uiState: HomeViewModel.UiState,
    onLoadNextPage: () -> Unit,
    onGenreItemSelected: (Int) -> Unit,
    checkIfGenreIsSelected: (Int) -> Boolean,
    onSaveFiltersButtonClicked: () -> Unit,
    setSelectedChip: (String) -> Unit,
    onMovieCLicked: (MovieUi) -> Unit,
    onSearchKeywordChanged: (String) -> Unit,
    onSearchPressed: () -> Unit,
    setOpenSearchBar: (Boolean) -> Unit,
    onSelectedYearChanged: (ClosedFloatingPointRange<Float>) -> Unit,
    onSelectedRatingChanged: (ClosedFloatingPointRange<Float>) -> Unit,
    onLanguageChanged: (String) -> Unit,
    onClearFiltersPressed: () -> Unit
) {
    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        sheetContent = {
            FilterSectionContent(
                genres = uiState.genres.map { it.genre },
                onGenreItemSelected = onGenreItemSelected,
                checkIfGenreIsSelected = checkIfGenreIsSelected,
                sheetState = sheetState,
                onSaveFiltersButtonClicked = onSaveFiltersButtonClicked,
                selectedYears = uiState.releaseYearsSelectedRange,
                selectedRating = uiState.ratingSelectedRange,
                onSelectedYearChanged = onSelectedYearChanged,
                onSelectedRatingChanged = onSelectedRatingChanged,
                onLanguageChanged = onLanguageChanged,
                selectedLanguage = uiState.selectedLanguage,
                languages = uiState.languages,
                onClearFiltersPressed = onClearFiltersPressed
            )
        },
        backgroundColor = Color.Black,
        sheetBackgroundColor = Color.Black,
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                if (!uiState.openSearchBar) {
                    Column(
                        modifier = Modifier
                            .wrapContentSize()
                            .background(colorResource(id = R.color.dark_grey))
                            .padding(top = 12.dp, start = 12.dp, end = 12.dp)
                    ) {
                        WatchNowSection(onClickOnSearch = {
                            setOpenSearchBar(true)
                        })
                        Row {
                            Box {
                                Icon(
                                    painter = if (uiState.isFilterActive) {
                                        painterResource(id = R.drawable.ic_filter_active)
                                    } else {
                                        painterResource(id = R.drawable.ic_filter)
                                    },
                                    contentDescription = "Filter",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .padding(start = 8.dp, top = 22.dp)
                                        .size(32.dp)
                                        .clickable {
                                            scope.launch {
                                                if (sheetState.isExpanded) {
                                                    sheetState.collapse()
                                                } else {
                                                    sheetState.expand()
                                                }
                                            }
                                        }
                                )
                                if (uiState.isFilterActive) {
                                    Box(
                                        modifier = Modifier
                                            .wrapContentWidth()
                                            .padding(top = 16.dp, start = 24.dp)
                                            .align(Alignment.TopEnd)
                                            .zIndex(2f)
                                            .border(
                                                width = 1.dp,
                                                color = colorResource(
                                                    id = R.color.dark_grey
                                                ),
                                                shape = CircleShape
                                            )
                                            .clip(CircleShape)
                                            .background(Color.White)
                                            .padding(start = 6.dp, end = 6.dp)

                                    ) {
                                        Text(text = uiState.noOfFiltersActive.toString(), color = Color.Black)
                                    }
                                }
                            }
                            ChipSection(chips = uiState.chips, setSelectedChip = setSelectedChip)
                        }
                    }
                } else {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp),
                        text = "Finder",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontFamily = FontFamily(Font(R.font.graphik_light)),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    SearchBarSection(
                        keywordTexted = uiState.keywordTexted,
                        onSearchKeywordChanged = onSearchKeywordChanged,
                        openSearchBar = uiState.openSearchBar,
                        onSearchPressed = { onSearchPressed() },
                        setOpenSearchBar = setOpenSearchBar
                    )
                }

                MoviesSection(
                    movies = uiState.moviesList,
                    onLoadNextPage = onLoadNextPage,
                    isLoading = uiState.isLoading,
                    onMovieCLicked = onMovieCLicked,
                    areMoviesLoading = uiState.areMoviesLoading
                )
            }
        }
    )
}

@Composable
fun MovieItem(
    position: Int,
    movieUi: MovieUi,
    onClick: (Int) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .heightIn(max = 350.dp)
            .fillMaxHeight()
            .clickable(indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onClick(position) }
            .padding(12.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color = colorResource(id = R.color.dark_grey))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .aspectRatio(5f / 4f),
                contentScale = ContentScale.Crop,
                model = movieUi.imageUrl,
                contentDescription = null
            )
            Column {
                Text(
                    text = "${movieUi.title} (${movieUi.releaseDate})",
                    color = Color.White,
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.titleLarge.copy(lineHeight = 20.sp),
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                )
                RatingStar(rating = movieUi.rating.toFloat() / 2f)
                Text(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    text = movieUi.description,
                    maxLines = 9,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.titleLarge.copy(lineHeight = 20.sp),
                )
            }
        }
    }
}
