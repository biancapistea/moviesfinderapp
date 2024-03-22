package com.example.moviesfinderapp.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviesfinderapp.ui.models.MovieUi
import com.example.moviesfinderapp.ui.screens.dashboard.HomeScreen
import com.example.moviesfinderapp.ui.screens.moviedetails.MovieDetailsScreen
import com.example.moviesfinderapp.ui.screens.splash.SplashScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destinations.Splash.name
) {
    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable(Destinations.Splash.name) {
                SplashScreen(
                    viewModel = hiltViewModel(),
                    onNavigateToDashboard = { navController.navigate(Destinations.Home.name) },
                )
            }
            composable(Destinations.Home.name) {
                HomeScreen(
                    homeViewModel = hiltViewModel(),
                    onMovieCLicked = { movieClicked ->
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            key = "movieClicked",
                            value = movieClicked
                        )
                        navController.navigate("${Destinations.MovieDetails.name}/${movieClicked.id}")
                    }
                )
            }
            composable("${Destinations.MovieDetails.name}/{movieId}") { backStackEntry ->
                val movieClicked = navController.previousBackStackEntry?.savedStateHandle?.get<MovieUi>(
                        "movieClicked"
                    )
                if (movieClicked != null) {
                    MovieDetailsScreen(
                        viewModel = hiltViewModel(backStackEntry),
                        movie = movieClicked,
                        onBackPressed = {
                            navController.popBackStack(
                                route = Destinations.Home.name,
                                inclusive = false
                            )
                        }
                    )
                }
            }
        }
    }
}