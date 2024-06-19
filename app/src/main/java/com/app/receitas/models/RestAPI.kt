package com.app.receitas.models

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RestAPI {
    @GET("api/receitas/by-UID")
     suspend fun getAllDatas(): Map<String, List<Recipe>>


    companion object {
        private val retrofitServices: RestAPI by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl("API Address")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofit.create(RestAPI::class.java)
        }

        fun getInstance(): RestAPI {
            return retrofitServices
        }
    }
}
