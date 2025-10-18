@file:OptIn(ExperimentalLayoutApi::class)

package com.plcoding.bookpedia.book.presentation.book_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.description_not_available
import cmp_bookpedia.composeapp.generated.resources.languages
import cmp_bookpedia.composeapp.generated.resources.pages
import cmp_bookpedia.composeapp.generated.resources.rating
import cmp_bookpedia.composeapp.generated.resources.synopsis
import com.plcoding.bookpedia.book.presentation.book_detail.components.BlurredImageBackground
import com.plcoding.bookpedia.book.presentation.book_detail.components.BookChip
import com.plcoding.bookpedia.book.presentation.book_detail.components.ChipSize
import com.plcoding.bookpedia.book.presentation.book_detail.components.TitledContent
import com.plcoding.bookpedia.core.presentation.SandYellow
import org.jetbrains.compose.resources.stringResource
import kotlin.math.round

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

@OptIn(ExperimentalLayoutApi::class)
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
        content = {
            if (state.book != null) {
                Column(
                    modifier = Modifier
                        .widthIn(max = 700.dp)
                        .fillMaxWidth()
                        .padding(
                            vertical = 16.dp,
                            horizontal = 24.dp
                        )
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = state.book.title,
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = state.book.authors.joinToString(),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                    Row(
                        modifier = Modifier.padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        state.book.averageRating?.let { rating ->
                            TitledContent(
                                title = stringResource(Res.string.rating)
                            ) {

                                BookChip {
                                    Row {
                                        Text(
                                            text = "${round(rating * 10) / 10.0}"
                                        )
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = null,
                                            tint = SandYellow
                                        )
                                    }
                                }
                            }
                        }

                        state.book.numPages?.let { pages ->
                            TitledContent(
                                title = stringResource(Res.string.pages)
                            ) {
                                BookChip {
                                    Text(
                                        text = "$pages"
                                    )
                                }
                            }
                        }
                    }

                    Languages(state)

                    Synopsis(state)
                }
            }
        },
    )
}

@Composable
private fun Languages(state: BookDetailState) {
    if (!state.book?.languages.isNullOrEmpty()) {
        TitledContent(
            title = stringResource(Res.string.languages),
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            FlowRow(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.wrapContentSize(Alignment.Center),
            ) {
                state.book.languages.forEach { language ->
                    BookChip(
                        modifier = Modifier.padding(2.dp),
                        chipSize = ChipSize.SMALL
                    ) {
                        Text(
                            text = language.uppercase(),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ColumnScope.Synopsis(state: BookDetailState) {
    Text(
        text = stringResource(Res.string.synopsis),
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier
            .align(Alignment.Start)
            .fillMaxWidth()
            .padding(
                top = 24.dp,
                bottom = 8.dp
            )
    )

    if (state.isLoading) {
        CircularProgressIndicator()
    } else {
        Text(
            text = state.book?.description ?: stringResource(Res.string.description_not_available),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Justify,
            color = Color.Black.copy(alpha = 0.4f).takeIf { state.book?.description != null }
                ?: Color.Black,
            modifier = Modifier
                .padding(vertical = 8.dp)
        )
    }
}