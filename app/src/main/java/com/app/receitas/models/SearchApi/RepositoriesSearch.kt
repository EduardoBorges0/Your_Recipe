package com.app.receitas.models.SearchApi

import com.app.receitas.models.Recipe

class RepositoriesSearch(private val retrofitRepositories: SearchRestApi) {
        suspend fun getAllTitles(title : String): Map<String, List<Recipe>> {
            return retrofitRepositories.getAllTitles(title)
        }
}