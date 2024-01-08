package com.example.moviesfinderapp.ui.screens.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.movies.R

@Composable
fun SplashScreen(onNavigateToDashboard: () -> Unit = {}, viewModel: SplashViewModel) {
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    if (isLoading) {
        SplashScreenContent()
    } else {
        LaunchedEffect(Unit) {
            onNavigateToDashboard()
        }
    }
}

@Composable
@Preview
fun SplashScreenContent() {
    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = {
                    OvershootInterpolator(7f).getInterpolation(it)
                }
            )
        )
    }
    Scaffold(
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.White),
                contentAlignment = Alignment.Center,
                content = {
                    Image(
                        modifier = Modifier
                            .wrapContentSize()
                            .scale(scale = scale.value),
                        painter = painterResource(id = R.drawable.ic_splash),
                        contentDescription = null
                    )
                }
            )
        }
    )
}
