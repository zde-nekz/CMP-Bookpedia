package com.plcoding.bookpedia

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.plcoding.bookpedia.book.data.network.KtorRemoteBookDataSource
import com.plcoding.bookpedia.book.data.repository.DefaultBookRepository
import com.plcoding.bookpedia.book.presentation.book_list.BookListScreenRoot
import com.plcoding.bookpedia.book.presentation.book_list.BookListViewModel
import com.plcoding.bookpedia.core.data.HttpClientFactory
import io.ktor.client.engine.HttpClientEngine
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(engine: HttpClientEngine) {
    MaterialTheme {
        BookListScreenRoot(
            viewModel = remember {
                BookListViewModel(
                    bookRepository = DefaultBookRepository(
                        KtorRemoteBookDataSource(
                            httpClient = HttpClientFactory.create(engine)
                        )
                    )
                )
            },
            onBookClick = {})
    }
}