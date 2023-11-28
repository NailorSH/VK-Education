package com.nailorsh.picsnet.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nailorsh.picsnet.R
import com.nailorsh.picsnet.network.PicsPhoto
import com.nailorsh.picsnet.ui.theme.PicsNetTheme

@Composable
fun HomeScreen(
    picsUiState: PicsUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onCardClicked: (String) -> Unit = {}
) {
    when (picsUiState) {
        is PicsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())

        is PicsUiState.Success -> PhotosGridScreen(
            picsUiState.photos,
            modifier = modifier,
            onCardClicked = onCardClicked
        )

        is PicsUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.size(100.dp))
    }

//    Image(
//        modifier = modifier.size(200.dp),
//        painter = painterResource(R.drawable.loading_img),
//        contentDescription = stringResource(R.string.loading)
//    )
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_connection_error),
            contentDescription = ""
        )
        Text(
            text = stringResource(R.string.loading_failed),
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun ResultScreen(photos: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(text = photos)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotosGridScreen(
    photos: List<PicsPhoto>,
    modifier: Modifier = Modifier,
    onCardClicked: (String) -> Unit = {}
) {

//    Реализация с карточками, сохраняющими пропорции
    LazyVerticalStaggeredGrid(
        modifier = modifier,
        columns = StaggeredGridCells.Adaptive(150.dp),
        contentPadding = PaddingValues(4.dp),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            items(photos) { PicsPhotoCard(
                photo = it,
                onCardClicked = onCardClicked
            ) }
        }
    )

//    Реализация с карточками фиксированного размера
//
//    LazyVerticalGrid(
//        columns = GridCells.Adaptive(150.dp),
//        modifier = modifier.fillMaxWidth(),
//        contentPadding = PaddingValues(4.dp)
//    ) {
//        items(items = photos, key = { photo -> photo.id }) { photo ->
//            PicsPhotoCard(
//                photo,
//                modifier = modifier
//                    .padding(4.dp)
//                    .fillMaxWidth()
//                    .aspectRatio(1.5f)
//            )
//        }
//    }
}

@Composable
fun PicsPhotoCard(photo: PicsPhoto, modifier: Modifier = Modifier, onCardClicked: (String) -> Unit = {}) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(photo.imgSrc)
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.pics_photo),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onCardClicked(photo.id) }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    PicsNetTheme {
        ResultScreen(stringResource(R.string.placeholder_result))
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    PicsNetTheme {
        ErrorScreen({})
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    PicsNetTheme {
        LoadingScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun PhotosGridScreenPreview() {
    PicsNetTheme {
        val mockData = List(10) { PicsPhoto("$it", "") }
        PhotosGridScreen(mockData)
    }
}