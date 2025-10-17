package com.plcoding.bookpedia.book.presentation.book_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.pages
import cmp_bookpedia.composeapp.generated.resources.rating
import com.plcoding.bookpedia.book.presentation.book_detail.components.BlurredImageBackground
import com.plcoding.bookpedia.book.presentation.book_detail.components.BookChip
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
                                Row {
                                    BookChip {
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
                }
            }
        },
    )
}