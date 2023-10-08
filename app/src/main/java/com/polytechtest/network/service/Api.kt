package com.polytechtest.network.service

import com.polytechtest.network.entity.catogiries.book_list.BookListAnswer
import com.polytechtest.network.entity.catogiries.CategoriesAnswer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("lists/names.json")
    suspend fun getCategories(): Response<CategoriesAnswer>

    @GET("lists/current/{name}.json")
    suspend fun getBookListByName(@Path("name") name: String): Response<BookListAnswer>

}