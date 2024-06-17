package com.app.receitas.viewModel.api


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.receitas.models.PostApi.RepositoriesPost
import com.app.receitas.models.Recipe
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch

class PostViewModelApi(private val repository: RepositoriesPost) : ViewModel() {
    private val _postResult = MutableLiveData<Boolean>()
    val postResult: LiveData<Boolean> get() = _postResult

    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()

    fun postRecipe(title : String, description : String, author : String, image : String, UID : String) {
        val recipePost = Recipe(
            title = title,
            description = description,
            author = author,
            image = image,
            UID = UID
        )

        viewModelScope.launch {
            try {
                val jsonArrayRecipe = gson.toJsonTree(listOf(recipePost)).asJsonArray
                Log.d("JSON_REQUEST", "JSON being sent: $jsonArrayRecipe")
                val result = repository.postRecipe(jsonArrayRecipe)
                _postResult.value = true
                Log.e("Foi", "Post com Sucesso $result")

            } catch (e: Exception) {
                Log.e("API_ERROR", "Erro ao fazer Post dos dados da API: ${e.message}")
            }
        }

    }
}
