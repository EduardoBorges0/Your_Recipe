package com.app.receitas.models.PostApi

import com.google.gson.JsonArray
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface PostRestApi {
    @POST("api/receitas")
    suspend fun postRecipe(@Body recipe: JsonArray): Response<Unit>

    companion object {
        private val retrofitServices: PostRestApi by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl("API Address")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofit.create(PostRestApi::class.java)
        }

        fun getInstance(): PostRestApi {
            return retrofitServices
        }
    }
}
