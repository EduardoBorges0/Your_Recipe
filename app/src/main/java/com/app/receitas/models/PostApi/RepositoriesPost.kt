package com.app.receitas.models.PostApi

import com.google.gson.JsonArray

class RepositoriesPost(private val postRestApi: PostRestApi) {
    suspend fun postRecipe(recipe: JsonArray): Boolean {
        val response = postRestApi.postRecipe(recipe)
        return response.isSuccessful
    }
}
