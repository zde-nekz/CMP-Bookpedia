package com.plcoding.bookpedia.book.presentation.book_detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.bookpedia.book.presentation.book_detail.components.BlurredImageBackground

@Composable
fun BookDetailScreenRoot(
    viewModel: BookDetailViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BookDetailScreen(
        state = state,
        onAction = { action ->
            if (action is BookDetailAction.OnBackClick) {
                onBackClick()
            } else {
                viewModel.onAction(action)
            }
        })

}

@Composable
private fun BookDetailScreen(
    modifier: Modifier = Modifier,
    state: BookDetailState,
    onAction: (BookDetailAction) -> Unit
) {
    BlurredImageBackground(
        modifier = modifier.fillMaxSize(),
        imageUrl = state.book?.imageUrl,
        isFavorite = state.isFavorite,
        onFavoriteClicked = {
            onAction(BookDetailAction.OnFavoriteClicked)
        },
        onBackClicked = {
            onAction(BookDetailAction.OnBackClick)
        },
        content = {},
    )
}