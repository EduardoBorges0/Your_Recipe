package com.app.receitas.view.navigations

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.app.receitas.R
import com.app.receitas.models.Recipe
import com.app.receitas.models.Repositories
import com.app.receitas.models.RestAPI
import com.app.receitas.view.navigations.ui.theme.ReceitasTheme
import com.app.receitas.view.navigations.ui.theme.YellowButton
import com.app.receitas.viewModel.api.MainViewModelApi

private lateinit var viewModelApi: MainViewModelApi
private lateinit var recipe: Recipe

class SearchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        recipe = Recipe("", "", "", "", "")
        viewModelApi = MainViewModelApi(Repositories(RestAPI.getInstance()))
        setContent {
            ReceitasTheme {
                SearchView(viewModelApi)
            }
        }
    }
}

@Composable
fun SearchView(viewModelApi: MainViewModelApi) {
    val (searchValue, setSearchValue) = remember { mutableStateOf("") }
    val (searchResults, setSearchResults) = remember { mutableStateOf(listOf<Recipe>()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.your_recipe),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = searchValue,
                onValueChange = {
                    setSearchValue(it)
                    searchRecipes(viewModelApi, it, setSearchResults)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .clip(RoundedCornerShape(8.dp)),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon"
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    textColor = Color.Black,
                )
            )

            if (searchResults.isNotEmpty()) {
                TitleItem(searchResults)
            }
        }
    }
}
@Composable
fun TitleItem(searchResults: List<Recipe>) {
    val context = LocalContext.current as ComponentActivity
    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
    ) {
        items(searchResults) { recipe ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 35.dp, horizontal = 28.dp)
                    .clickable {
                        val intent = Intent(context, ClickRecipe::class.java).apply {
                            putExtra("recipeTitle", recipe.title)
                            putExtra("recipeDescription", recipe.description)
                            putExtra("recipeAuthor", recipe.author)
                            putExtra("recipeUID", recipe.UID)
                            putExtra("recipeImage", recipe.image)
                        }
                        context.startActivity(intent)
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = recipe.image),
                    contentDescription = "image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .clip(RoundedCornerShape(8.dp, 8.dp, 0.dp, 0.dp)),
                    contentScale = ContentScale.FillWidth
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(RoundedCornerShape(0.dp, 0.dp, 8.dp, 8.dp))
                        .background(YellowButton)
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                ) {
                    Text(
                        text = recipe.title.capitalizeFirstLetter(),
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 18.sp
                    )
                    Text(
                        text = "By ${recipe.author}",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

private fun searchRecipes(
    viewModelApi: MainViewModelApi,
    searchValue: String,
    setSearchResults: (List<Recipe>) -> Unit
) {
    if (searchValue.isNotBlank()) {
        val results = viewModelApi.recipes.value?.filter { it.title.contains(searchValue, ignoreCase = true) }
        setSearchResults(results ?: emptyList())
    } else {
        setSearchResults(emptyList())
    }
}


