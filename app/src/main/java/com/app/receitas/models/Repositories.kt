package com.app.receitas.models

class Repositories(private val retrofitRepositories: RestAPI) {
    suspend fun getAllDatas(): Map<String, List<Recipe>> {
        return retrofitRepositories.getAllDatas()
    }

}
