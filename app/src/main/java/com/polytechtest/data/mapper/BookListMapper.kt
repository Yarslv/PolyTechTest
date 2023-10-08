package com.polytechtest.data.mapper

import com.polytechtest.arch.Mapper
import com.polytechtest.domain.entity.BookListHolder
import com.polytechtest.domain.entity.BookListModel
import com.polytechtest.domain.entity.BookModel
import com.polytechtest.network.book_list.BookListAnswer

class BookListMapper : Mapper<BookListAnswer, BookListModel> {
    override fun toDomain(model: BookListAnswer): BookListModel {
        val title = model.results.display_name
        val books = BookListHolder(model.results.books.map {
            BookModel(
                name = it.title,
                description = it.description,
                author = it.author,
                publisher = it.publisher,
                imageLink = it.book_image,
                rank = it.rank.toString(),
                buyLink = it.amazon_product_url
            )
        })
        return BookListModel(0, title, model.results.list_name_encoded, books)
    }
}