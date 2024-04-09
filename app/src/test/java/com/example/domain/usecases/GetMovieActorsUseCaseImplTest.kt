package com.example.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.moviesfinderapp.ui.screens.dashboard.HomeViewModel
import com.nhaarman.mockito_kotlin.mock
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
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

    @Test
    fun `test when click on a chips the text is the selected one`(){
        objUnderTest.setSelectedChip("Top rated")
        assertEquals(objUnderTest.uiState.value.selectedChip, "Top rated")
    }
}