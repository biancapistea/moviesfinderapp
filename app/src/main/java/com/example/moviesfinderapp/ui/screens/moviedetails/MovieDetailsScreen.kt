package com.example.moviesfinderapp.ui.screens.moviedetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.countryapp.ui.components.customgriditems.gridItems
import com.example.movies.R
import com.example.moviesfinderapp.ui.components.chips.MultipleSelectionChips
import com.example.moviesfinderapp.ui.components.rating.RatingStar
import com.example.moviesfinderapp.ui.components.text.ParagraphTextComponent
import com.example.moviesfinderapp.ui.components.video.YoutubePlayer
import com.example.moviesfinderapp.ui.models.ActorUi
import com.example.moviesfinderapp.ui.models.MovieUi

@Composable
fun MovieDetailsScreen(
    viewModel: MovieDetailsViewModel,
    movie: MovieUi,
    onBackPressed: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    MovieDetailsContent(movie, onBackPressed, uiState, setPlayVideo = viewModel::setPlayVideo)
}

@Composable
fun MovieDetailsContent(
    movie: MovieUi,
    onBackPressed: () -> Unit,
    uiState: MovieDetailsViewModel.UiState,
    setPlayVideo: (Boolean) -> Unit
) {
    Scaffold(
        content = { paddingValues ->
            Box(modifier = Modifier.background(Color.Black)) {
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .zIndex(2f)
                        .align(Alignment.TopStart)
                        .padding(top = 24.dp, start = 24.dp, end = 24.dp),
                ) {
                    Row(Modifier.fillMaxWidth()) {
                        Box(Modifier.fillMaxWidth()) {
                            Image(
                                painter = painterResource(R.drawable.ic_back),
                                modifier = Modifier
                                    .height(48.dp)
                                    .align(Alignment.TopStart)
                                    .clickable { onBackPressed() },
                                contentDescription = null
                            )
//                            Image(
//                                painter = painterResource(R.drawable.ic_save),
//                                modifier = Modifier
//                                    .height(48.dp)
//                                    .size(28.dp)
//                                    .align(Alignment.TopEnd),
//                                contentDescription = null
//                            )
                        }
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = paddingValues.calculateBottomPadding()),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    item {
                        Column(
                            Modifier
                                .fillMaxWidth()
                        ) {
                            Box(modifier = Modifier.fillMaxWidth()) {
                                if (!uiState.playVideo) {
                                    Column(
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .zIndex(2f)
                                            .fillMaxWidth()
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.PlayArrow,
                                            contentDescription = "Play",
                                            tint = Color.White,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .size(60.dp)
                                                .clickable {
                                                    setPlayVideo(true)
                                                }
                                        )
                                        ParagraphTextComponent(
                                            paddingValues = PaddingValues(
                                                horizontal = 20.dp,
                                                vertical = 2.dp
                                            ),
                                            text = "Play trailer",
                                            color = Color.White,
                                            fontSize = 18.sp, textAlign = TextAlign.Center
                                        )
                                    }
                                    AsyncImage(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .aspectRatio(8f / 5f)
                                            .drawWithCache {
                                                val gradient = Brush.verticalGradient(
                                                    colors = listOf(
                                                        Color.Transparent,
                                                        Color.Black.copy(alpha = 0.5F)
                                                    ),
                                                    startY = size.height,
                                                    endY = 0.3f
                                                )
                                                onDrawWithContent {
                                                    drawContent()
                                                    drawRect(
                                                        gradient,
                                                        blendMode = BlendMode.Multiply
                                                    )
                                                }
                                            },
                                        contentScale = ContentScale.Crop,
                                        model = movie.imageUrl,
                                        contentDescription = null,
                                        filterQuality = FilterQuality.High
                                    )
                                } else {
                                    if (uiState.videoUrl.isNotEmpty()) {
                                        YoutubePlayer(
                                            modifier = Modifier
                                                .aspectRatio(8f / 5f),
                                            youtubeVideoId = uiState.videoUrl,
                                            lifecycleOwner = LocalLifecycleOwner.current,
                                            onVideoEnded = setPlayVideo
                                        )
                                    }
                                }
                            }
                            Text(
                                text = "${movie.title} (${movie.releaseDate})",
                                color = Color.White,
                                fontSize = 32.sp,
                                lineHeight = 40.sp,
                                modifier = Modifier
                                    .align(Alignment.Start)
                                    .padding(horizontal = 20.dp, vertical = 8.dp)
                            )
                            RatingStar(
                                rating = movie.rating.toFloat() / 2f,
                                modifier = Modifier.padding(start = 8.dp),
                                maxRating = 5,
                                showRatingScore = false
                            )
                            MultipleSelectionChips(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 20.dp),
                                chips = movie.genres
                            )
                            Text(
                                text = "Storyline",
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .align(Alignment.Start)
                                    .padding(horizontal = 20.dp, vertical = 8.dp)
                            )
                            ParagraphTextComponent(
                                paddingValues = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                                text = movie.description,
                                color = Color.White,
                                fontSize = 24.sp, textAlign = TextAlign.Start
                            )
                            Text(
                                text = "Top cast",
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .align(Alignment.Start)
                                    .padding(horizontal = 20.dp, vertical = 8.dp)
                            )
                        }
                    }
                    gridItems(
                        horizontalArrangement = Arrangement.spacedBy(
                            24.dp,
                            alignment = Alignment.CenterHorizontally
                        ),
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(top = 24.dp),
                        data = uiState.actors,
                        columnCount = 3,
                        itemContent = { itemData ->
                            ActorItem(itemData)
                        }
                    ) {}
                }
            }
        }
    )

}

@Composable
fun ActorItem(
    actorUi: ActorUi,
) {
    Column(
        modifier = Modifier
            .width(110.dp)
            .height(160.dp)
            .padding(start = 20.dp, end = 8.dp)
    ) {
        if (actorUi.profilePath.isNullOrEmpty().not()) {
            AsyncImage(
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(74.dp)
                    .clip(CircleShape),
                model = actorUi.profilePath,
                contentDescription = null
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.img_no_picture_profile),
                contentDescription = "No picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(74.dp)
                    .clip(CircleShape)
            )
        }
        Text(
            text = actorUi.name,
            color = Color.White,
            fontSize = 16.sp,
            style = MaterialTheme.typography.titleLarge.copy(lineHeight = 20.sp),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 4.dp),
        )
        Text(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 4.dp),
            text = actorUi.character,
            color = Color.White,
            fontSize = 14.sp,
            style = MaterialTheme.typography.titleLarge.copy(lineHeight = 20.sp),
        )
    }
}
