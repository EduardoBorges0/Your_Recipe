package com.app.receitas.models

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.Path

interface DeleteRestApi {
    @DELETE("api/receitas/delete/{UID}/delete-{title}")
    suspend fun deleteRecipe(
        @Path("UID") uid: String,
        @Path("title") title: String
    ): Response<Unit>

    companion object {
        private val retrofitServices: DeleteRestApi by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl("API Adress")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofit.create(DeleteRestApi::class.java)
        }

        fun getInstance(): DeleteRestApi {
            return retrofitServices
        }
    }
}
