package com.app.receitas.viewModel.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.receitas.models.Recipe
import com.app.receitas.models.Repositories
import kotlinx.coroutines.launch

class MainViewModelApi(private val repository: Repositories) : ViewModel() {

    val recipes = MutableLiveData<List<Recipe>>()
    val errorMessage = MutableLiveData<String>()

    fun getAllRecipes() {
        viewModelScope.launch {
            try {
                val response = repository.getAllDatas()
                val allRecipes = response.values.flatten()
                Log.e("RESPOSTA", "Resposta da Api: ${response}")
                recipes.postValue(allRecipes)
            } catch (e: Exception) {
                errorMessage.postValue("Erro ao obter dados da API: ${e.message}")
                Log.e("API_ERROR", "Erro ao obter dados da API: ${e.message}")
            }
        }
    }

}
