package test.eugene.sharephoto.feature.photoitem.ui.photofeed

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.IosShare
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import kotlinx.coroutines.launch
import test.eugene.sharephoto.core.domain.model.Photo
import test.eugene.sharephoto.core.ui.CommonStringR
import test.eugene.sharephoto.feature.photoitem.ui.navigation.ScreenPhoto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoFeedScreen(
    navController: NavController,
    viewModel: PhotoFeedViewModel = hiltViewModel(),
    onPhotoClick: (Photo) -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Show error in snackbar if present
    LaunchedEffect(uiState.error) {
        uiState.error?.let { error ->
            scope.launch {
                snackbarHostState.showSnackbar(error)
                viewModel.dismissError()
            }
        }
    }

    val pullRefreshState = rememberPullToRefreshState()
    HandleScreenResume(navController, viewModel::onScreenResumed)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(CommonStringR.photo_feed)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        PullToRefreshBox(
            state = pullRefreshState,
            isRefreshing = uiState.isRefreshing,
            onRefresh = viewModel::refresh,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                if (uiState.isLoading && uiState.photos.isEmpty()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    PhotoFeedContent(
                        photos = uiState.photos,
                        isLoadingMore = uiState.isLoadingMore,
                        onPhotoClick = onPhotoClick,
                        onLikeClick = viewModel::toggleLike,
                        onLoadMore = viewModel::loadNextPage,
                    )
                }
            }
        }

    }
}

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun HandleScreenResume(navController: NavController, onScreenResumed: () -> Unit = {}) {
    val navBackStackEntry =
        remember { navController.getBackStackEntry(ScreenPhoto.PhotoFeed.route) }
    val lifecycle = navBackStackEntry.getLifecycle()

    DisposableEffect(lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                onScreenResumed()
            }
        }

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}

@Composable
fun PhotoFeedContent(
    photos: List<Photo>,
    isLoadingMore: Boolean,
    onPhotoClick: (Photo) -> Unit,
    onLikeClick: (String) -> Unit,
    onLoadMore: () -> Unit,
) {
    val lazyListState = rememberLazyListState()

    // Detect when to load more content
    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisibleItem = lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem != null &&
                    lastVisibleItem.index >= photos.size - 3 &&
                    !isLoadingMore
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) onLoadMore()
    }

    LazyColumn(
        state = lazyListState,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp)
    ) {
        items(photos, key = { it.id }) { photo ->
            PhotoItem(
                photo = photo,
                onClick = { onPhotoClick(photo) },
                onLikeClick = { onLikeClick(photo.id) },
            )
        }

        if (isLoadingMore) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun PhotoItem(
    photo: Photo,
    onClick: () -> Unit,
    onLikeClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Author header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = photo.author,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }

        // Photo
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .clip(RoundedCornerShape(12.dp))
                .clickable(onClick = onClick),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        ) {
            SubcomposeAsyncImage(
                model = photo.downloadUrl,
                contentDescription = stringResource(
                    CommonStringR.photo_by,
                    photo.author,
                ),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                loading = {
                    Box(Modifier.fillMaxSize()) {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }
                },
            )
        }

        // Actions
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row {
                // Like button
                IconButton(onClick = onLikeClick) {
                    Icon(
                        imageVector =
                            if (photo.isLiked) Icons.Filled.Favorite
                            else Icons.Filled.FavoriteBorder,
                        contentDescription = stringResource(CommonStringR.like),
                        tint = if (photo.isLiked) Color.Red else MaterialTheme.colorScheme.onSurface,
                    )
                }

                // Comment button
                IconButton(onClick = { /* Open comments */ }) {
                    Icon(
                        imageVector = Icons.Outlined.ChatBubbleOutline,
                        contentDescription = stringResource(CommonStringR.comment),
                    )
                }

                // Share button
                IconButton(onClick = { /* Share photo */ }) {
                    Icon(
                        imageVector = Icons.Outlined.IosShare,
                        contentDescription = stringResource(CommonStringR.share),
                    )
                }
            }
        }

    }
}