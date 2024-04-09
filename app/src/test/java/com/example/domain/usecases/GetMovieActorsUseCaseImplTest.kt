package com.example.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.model.ListMovies
import com.example.domain.model.Movie
import com.example.domain.model.Resource
import com.example.moviesfinderapp.ui.screens.dashboard.HomeViewModel
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class HomeViewModelTest {
    @Mock
    private lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase
    private lateinit var getMoviesBasedOnKeywordUseCase: GetMoviesBasedOnKeywordUseCase
    private lateinit var getMoviesBasedOnFiltersUseCase: GetMoviesBasedOnFiltersUseCase
    private lateinit var getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase
    private val dispatcher = StandardTestDispatcher()

    private val objUnderTest by lazy {
        HomeViewModel(
            getPopularMoviesUseCase,
            getMoviesBasedOnKeywordUseCase,
            getMoviesBasedOnFiltersUseCase,
            getTopRatedMoviesUseCase
        )
    }

    @Before
    fun setUp() {
        getMoviesBasedOnKeywordUseCase = mock() // Initialize the mock object
        getTopRatedMoviesUseCase = mock()
        getPopularMoviesUseCase = mock()
        getMoviesBasedOnFiltersUseCase = mock()
        // Initialize other dependencies if needed
    }

    @Before
    fun before() {
        Dispatchers.setMain(dispatcher)
    }

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @After
    fun tearDown() {
        Dispatchers.resetMain()

    }

    @Test
    fun `test get popular movies`() {
        assertEquals(objUnderTest.uiState.value.isLoading, false)
    }
}