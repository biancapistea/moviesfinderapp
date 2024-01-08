package com.example.moviesfinderapp.ui.screens.moviedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Resource
import com.example.domain.usecases.GetMovieActorsUseCase
import com.example.domain.usecases.GetMovieVideoUseCase
import com.example.moviesfinderapp.ui.models.ActorUi
import com.example.moviesfinderapp.ui.models.mapToComposeUi
import com.example.moviesfinderapp.ui.models.toComposeUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieVideoUseCase: GetMovieVideoUseCase,
    private val getMovieActorsUseCase: GetMovieActorsUseCase,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    private val movieId: String = checkNotNull(savedStateHandle["movieId"])

    init {
        getMovieBasedOnId(movieId.toInt())
        getMovieActors(movieId.toInt())
    }

    fun setPlayVideo(shouldPlay: Boolean) {
        _uiState.update { it.copy(playVideo = shouldPlay) }
    }

    private fun getMovieActors(movieId: Int?) {
        viewModelScope.launch {
            if (movieId != null) {
                getMovieActorsUseCase.getMovieActorsById(movieId).collectLatest { status ->
                    val actorsList =
                        status.data?.cast?.map { actor -> actor.mapToComposeUi() }
                            ?: emptyList()
                    when (status) {
                        is Resource.Success -> {
                            _uiState.update {
                                it.copy(
                                    actors = actorsList.take(8)
                                )
                            }
                        }

                        is Resource.Loading -> {}
                        is Resource.Error -> {}
                    }
                }
            }
        }
    }

    private fun getMovieBasedOnId(movieId: Int?) {
        viewModelScope.launch {
            if (movieId != null) {
                getMovieVideoUseCase.getMovieVideoById(movieId).collectLatest { status ->
                    val videosList =
                        status.data?.results?.map { videosList -> videosList.toComposeUiModel() }
                            ?: emptyList()
                    when (status) {
                        is Resource.Success -> {
                            _uiState.update {
                                it.copy(
                                    videoUrl = videosList.find { video ->
                                        video.type == "Trailer"
                                    }?.videoUrl ?: ""
                                )
                            }
                        }

                        is Resource.Loading -> {}
                        is Resource.Error -> {}
                    }
                }
            }
        }
    }

    data class UiState(
        val videoUrl: String = "",
        val type: String = "",
        val movieId: Int = 0,
        val playVideo: Boolean = false,
        val actors: List<ActorUi> = emptyList()
    )
}