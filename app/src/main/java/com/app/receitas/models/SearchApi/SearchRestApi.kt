package com.app.receitas.models.SearchApi

import com.app.receitas.models.Recipe
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface SearchRestApi {
    @GET("api/receitas/title/{title}")
    suspend fun getAllTitles(@Path("title") title: String): Map<String, List<Recipe>>
    companion object {
        private val retrofitServices: SearchRestApi by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl("API Address")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofit.create(SearchRestApi::class.java)
        }

        fun getInstance(): SearchRestApi {
            return retrofitServices
        }
    }
}
