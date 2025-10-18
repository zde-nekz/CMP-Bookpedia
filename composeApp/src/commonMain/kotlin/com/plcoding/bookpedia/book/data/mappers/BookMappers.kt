package com.plcoding.bookpedia.book.data.mappers

import com.plcoding.bookpedia.book.data.database.BookEntity
import com.plcoding.bookpedia.book.data.dto.SearchedBookDto
import com.plcoding.bookpedia.book.domain.Book

fun SearchedBookDto.toBook(): Book {

    val imageUrl = if (coverKey != null) {
        "https://covers.openlibrary.org/b/olid/${coverKey}-L.jpg"
    } else {
        "https://covers.openlibrary.org/b/id/${coverAlternativeKey}-L.jpg"
    }

    return Book(
        id = id.substringAfterLast("/"),
        title = title,
        imageUrl = imageUrl,
        authors = authorNames ?: emptyList(),
        description = null,
        languages = languages ?: emptyList(),
        firstPublishYear = firstPublishYear.toString(),
        averageRating = ratingsAverage,
        ratingCount = ratingsCount,
        numPages = numPagesMedian,
        numEditions = numEditions ?: 0
    )
}

fun Book.toBookEntity() = BookEntity(
    id = id,
    title = title,
    imageUrl = imageUrl,
    description = description,
    languages = languages,
    authors = authors,
    ratingsAverage = averageRating,
    ratingsCount = ratingCount,
    numPagesMedian = numPages,
    numEditions = numEditions,
    firstPublishYear = firstPublishYear
)

fun BookEntity.toBook() = Book(
    id = id,
    title = title,
    imageUrl = imageUrl,
    authors = authors,
    description = description,
    languages = languages,
    firstPublishYear = firstPublishYear,
    averageRating = ratingsAverage,
    ratingCount = ratingsCount,
    numPages = numPagesMedian,
    numEditions = numEditions
)