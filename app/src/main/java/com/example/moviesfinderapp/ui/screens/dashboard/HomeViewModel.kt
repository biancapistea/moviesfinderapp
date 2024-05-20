package com.example.moviesfinderapp.ui.screens.dashboard

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Resource
import com.example.domain.usecases.GetMoviesBasedOnFiltersUseCase
import com.example.domain.usecases.GetMoviesBasedOnKeywordUseCase
import com.example.domain.usecases.GetPopularMoviesUseCase
import com.example.domain.usecases.GetTopRatedMoviesUseCase
import com.example.moviesfinderapp.ui.models.ChipModel
import com.example.moviesfinderapp.ui.models.GenreItemModel
import com.example.moviesfinderapp.ui.models.MovieUi
import com.example.moviesfinderapp.ui.models.toComposeUiModel
import com.example.moviesfinderapp.ui.paginator.DefaultPaginator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class HomeViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getMoviesBasedOnKeywordUseCase: GetMoviesBasedOnKeywordUseCase,
    private val getMoviesBasedOnFiltersUseCase: GetMoviesBasedOnFiltersUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    private var paginator = DefaultPaginator<Int, MovieUi>(
        initialKey = _uiState.value.currentPage,
        onLoadUpdated = { isLoading ->
            _uiState.update { it.copy(isLoading = isLoading) }
        },
        onRequest = { nextPage ->
            onLoadNextPage(nextPage)
        },
    )

    init {
        populateChipsAndGenresAndLanguages()
        getPopularMovies(1)
    }

    private fun populateChipsAndGenresAndLanguages() {
        _uiState.update {
            it.copy(
                chips = getChips(),
                genres = getGenres(),
                languages = getLanguages()
            )
        }
    }

    fun onSearchKeywordChanged(keywordTexted: String) {
        _uiState.update { it.copy(keywordTexted = keywordTexted) }
    }

    fun onSearchBarOpened(openSearchBar: Boolean) {
        _uiState.update { it.copy(openSearchBar = openSearchBar) }
    }

    fun onLanguageChanged(language: String) {
        _uiState.update {
            it.copy(
                selectedLanguage = language
            )
        }
    }

    fun onSelectedRatingChanged(rating: ClosedFloatingPointRange<Float>) {
        _uiState.update {
            it.copy(
                ratingSelectedRange = rating,
            )
        }
    }

    fun onSelectedYearsChanged(years: ClosedFloatingPointRange<Float>) {
        _uiState.update {
            it.copy(
                releaseYearsSelectedRange = years
            )
        }
    }

    fun loadNextPage() {
        viewModelScope.launch {
            paginator.setNextPage(_uiState.value.currentPage)
            paginator.loadNextPage()
        }
    }

    fun onFiltersSelected() {
        _uiState.update {
            it.copy(isFilterActive = true)
        }
        getMoviesBasedOnFilters()
    }

    fun onSearchPressed() {
        _uiState.update {
            it.copy(isSearchActive = true)
        }
        getMoviesBasedOnKeyword()
    }

    private fun updateNumberOfFilters() {
        var filterNo = 0
        _uiState.value.apply {
            filterNo += genres.filter { it.isSelected }.size
            val defaultRating: ClosedFloatingPointRange<Float> = 0f..10f
            val defaultYears:ClosedFloatingPointRange<Float> = 1900f..Calendar.getInstance().get(
                Calendar.YEAR).toFloat()
            if (ratingSelectedRange.toString() != defaultRating.toString()) {
                filterNo += 1
            }
            if (releaseYearsSelectedRange.toString() != defaultYears.toString()) {
                filterNo += 1
            }
            if (selectedLanguage != "All") {
                filterNo += 1
            }
        }
        _uiState.update { it.copy(noOfFiltersActive = filterNo) }
    }

    private fun getMoviesBasedOnFilters(page: Int = 1) {
        updateNumberOfFilters()
        viewModelScope.launch {
            getMoviesBasedOnFiltersUseCase.getMoviesBasedOnFilters(
                page,
                lowestScore = _uiState.value.ratingSelectedRange.start,
                highestScore = _uiState.value.ratingSelectedRange.endInclusive,
                genres = _uiState.value.genres.filter { item -> item.isSelected }.map { it.genre },
                releaseDateLowest = _uiState.value.releaseYearsSelectedRange.start.toInt()
                    .toString(),
                releaseDateHighest = _uiState.value.releaseYearsSelectedRange.endInclusive.toInt()
                    .toString(),
                language = _uiState.value.selectedLanguage
            ).collectLatest { status ->
                when (status) {
                    is Resource.Success -> {
                        val moviesList =
                            status.data?.results?.map { moviesDomain -> moviesDomain.toComposeUiModel() }
                                ?: emptyList()
                        updateMoviesList(moviesList, page)
                    }

                    is Resource.Loading -> {
                        _uiState.update { it.copy(areMoviesLoading = true) }
                    }

                    is Resource.Error -> {
                        Log.d("Am intrat", status.message.toString())

                    }

                    else -> {}
                }
            }
        }
    }

    private fun onLoadNextPage(nextPage: Int) {
        if (_uiState.value.isFilterActive) {
            getMoviesBasedOnFilters(nextPage)
            return
        }

        if (_uiState.value.isSearchActive) {
            getMoviesBasedOnKeyword(nextPage)
            return
        }

        when (_uiState.value.selectedChip) {
            "Populars" -> {
                getPopularMovies(nextPage)
            }

            "Top rated" -> {
                onClickOnTopRatedMovies(nextPage)
            }

//            "Want to watch" -> {
//
//            }
        }
    }

    private fun getGenres(): List<GenreItemModel> {
        return listOf(
            GenreItemModel("Action", false),
            GenreItemModel("Adventure", false),
            GenreItemModel("Animation", false),
            GenreItemModel("Comedy", false),
            GenreItemModel("Crime", false),
            GenreItemModel("Drama", false),
            GenreItemModel("Family", false),
            GenreItemModel("Fantasy", false),
            GenreItemModel("History", false),
            GenreItemModel("Horror", false),
            GenreItemModel("Musical", false),
            GenreItemModel("Mystery", false),
            GenreItemModel("Romance", false),
            GenreItemModel("SF", false),
            GenreItemModel("Thriller", false),
            GenreItemModel("War", false),
            GenreItemModel("Western", false),
        )
    }

    private fun getLanguages(): List<String> {
        return listOf(
            "All",
            "English",
            "French",
            "Hindi",
            "Spanish",
            "Romanian",
            "German",
            "Italian"
        )
    }

    private fun getChips(): List<ChipModel> {
        return listOf(
            ChipModel(
                title = "Populars",
                onClick = { resetFilters() }
            ),
            ChipModel(
                title = "Top rated",
                onClick = { resetFilters() }
            ),
//            ChipModel(
//                title = "Want to watch",
//                onClick = { resetFilters() }
//            )
        )
    }

    fun setSelectedChip(chip: String) {
        _uiState.update { it.copy(selectedChip = chip) }
    }

    fun resetFilters() {
        _uiState.update {
            it.copy(
                isFilterActive = false,
                ratingSelectedRange = 0f..10f,
                releaseYearsSelectedRange = 1900f..2023f,
                genres = getGenres(),
                selectedLanguage = "All",
                noOfFiltersActive = 0
            )
        }
        onLoadNextPage(1)
    }

    private fun getMoviesBasedOnKeyword(page: Int = 1) {
        viewModelScope.launch {
            getMoviesBasedOnKeywordUseCase.getMoviesBasedOnKeyword(
                page,
                _uiState.value.keywordTexted
            ).collectLatest { status ->
                when (status) {
                    is Resource.Success -> {
                        val moviesList =
                            status.data?.results?.map { moviesDomain -> moviesDomain.toComposeUiModel() }
                                ?: emptyList()
                        updateMoviesList(moviesList, page)
                    }

                    is Resource.Loading -> {
                        _uiState.update { it.copy(areMoviesLoading = true) }
                    }

                    is Resource.Error -> {
                        Log.d("Am intrat", status.message.toString())

                    }

                    else -> {}
                }
            }
        }
    }

    fun onGenreItemSelected(index: Int) {
        _uiState.update {
            it.copy(genres = it.genres.mapIndexed { i, genreItemModel ->
                if (i == index) {
                    genreItemModel.copy(isSelected = !genreItemModel.isSelected)
                } else genreItemModel
            })
        }
    }

    private fun onClickOnTopRatedMovies(page: Int) {
        viewModelScope.launch {
            getTopRatedMoviesUseCase.getTopRatedMovies(page)
                .collectLatest { status ->
                    when (status) {
                        is Resource.Success -> {
                            val moviesList =
                                status.data?.results?.map { moviesDomain -> moviesDomain.toComposeUiModel() }
                                    ?: emptyList()
                            updateMoviesList(moviesList, page)
                        }

                        is Resource.Loading -> {
                            _uiState.update { it.copy(areMoviesLoading = true) }
                        }

                        is Resource.Error -> {
                            Log.d("Am intrat", status.message.toString())

                        }

                        else -> {}
                    }
                }
        }
    }

    private fun getPopularMovies(page: Int) {
        viewModelScope.launch {
            getPopularMoviesUseCase.getPopularMovies(page)
                .collectLatest { status ->
                    when (status) {
                        is Resource.Success -> {
                            val moviesList =
                                status.data?.results?.map { moviesDomain -> moviesDomain.toComposeUiModel() }
                                    ?: emptyList()
                            updateMoviesList(moviesList, page)
                            Log.d("Am intrat", "Cu succes")
                        }

                        is Resource.Loading -> {
                            _uiState.update {
                                it.copy(areMoviesLoading = true)
                            }
                        }

                        is Resource.Error -> {
                            Log.d("Am intrat", status.message.toString())
                        }

                        else -> {}
                    }
                }
        }
    }

    private fun updateMoviesList(moviesList: List<MovieUi>, page: Int) {
        if (page > 1) {
            val previousPageMovies = _uiState.value.moviesList
            _uiState.update {
                it.copy(
                    moviesList = previousPageMovies + moviesList,
                    currentPage = _uiState.value.currentPage + 1,
                    areMoviesLoading = false
                    //   isLoading = false
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    moviesList = moviesList,
                    currentPage = _uiState.value.currentPage + 1,
                    areMoviesLoading = false
                    //   isLoading = false
                )
            }
        }
    }

    fun checkIfGenreIsSelected(i: Int): Boolean {
        val genreItems = _uiState.value.genres.filterIndexed { index, _ -> index == i }
        return genreItems.first().isSelected
    }


    data class UiState(
        val moviesList: List<MovieUi> = emptyList(),
        val currentPage: Int = 1,
        val selectedChip: String = "Populars",
        val chips: List<ChipModel> = emptyList(),
        val genres: List<GenreItemModel> = emptyList(),
        val endReached: Boolean = false,
        val isLoading: Boolean = false,
        val areMoviesLoading: Boolean = false,
        val keywordTexted: String = "",
        val openSearchBar: Boolean = false,
        val isFilterActive: Boolean = false,
        val isSearchActive: Boolean = false,
        val selectedLanguage: String = "All",
        val languages: List<String> = emptyList(),
        val noOfFiltersActive: Int = 0,
        val ratingSelectedRange: ClosedFloatingPointRange<Float> = 0f..10f,
        val releaseYearsSelectedRange: ClosedFloatingPointRange<Float> = 1900f..Calendar.getInstance().get(
            Calendar.YEAR).toFloat(),
    )
}