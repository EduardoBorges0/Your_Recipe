package com.app.receitas.view.navigations

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.core.view.WindowCompat
import coil.compose.rememberAsyncImagePainter
import com.app.receitas.R
import com.app.receitas.models.Recipe
import com.app.receitas.models.Repositories
import com.app.receitas.models.RestAPI
import com.app.receitas.view.navigations.ui.theme.ReceitasTheme
import com.app.receitas.view.navigations.ui.theme.YellowButton
import com.app.receitas.viewModel.api.MainViewModelApi

private lateinit var viewModelApi: MainViewModelApi

class AnotherUserClick : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewModelApi = MainViewModelApi(Repositories(RestAPI.getInstance()))
        viewModelApi.getAllRecipes()
        WindowCompat.setDecorFitsSystemWindows(window, true)

        setContent {
            ReceitasTheme {
                val recipeUID = intent?.getStringExtra("recipe-UID") ?: ""
                val recipeAuthor = intent?.getStringExtra("recipe-username") ?: ""
                anotherUser(viewModelApi, recipeUID, recipeAuthor)
            }
        }
    }
}

@Composable
fun anotherUser(viewModelApi: MainViewModelApi, UID: String, Author: String) {
    val recipes by viewModelApi.recipes.observeAsState(initial = listOf())
    val errorMessage by viewModelApi.errorMessage.observeAsState(initial = "")

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
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top, // Alinhe os elementos ao topo
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(YellowButton)
                    .height(170.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_account_circle_24),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .padding(horizontal = 9.dp)
                        .size(120.dp)
                )
                Column {
                    Text(
                        text = Author,
                        modifier = Modifier
                            .padding(top = 60.dp)
                            .padding(horizontal = 10.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                    Text(
                        text = UID,
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .padding(horizontal = 10.dp),
                        fontSize = 15.sp,
                        color = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            if (recipes.isNotEmpty()) {
                LazyVerticalGrid(
                    GridCells.Fixed(3),
                    modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally).padding(14.dp)
                ) {
                    items(recipes) { recipe ->
                        RecipeItemAnother(recipe, UID)
                    }
                }
            } else if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, modifier = Modifier.padding(16.dp))
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = YellowButton)
                }
             }
        }
    }
}



@SuppressLint("SuspiciousIndentation")
@Composable
fun RecipeItemAnother(recipe: Recipe, UID: String) {
    val context = LocalContext.current as ComponentActivity
        if (recipe.UID == UID) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 9.dp, horizontal = 9.dp),
            ) {

                Image(
                    painter = rememberAsyncImagePainter(model = recipe.image),
                    contentDescription = "image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(96.dp)
                       .clip(GenericShape { size, _ ->
                            moveTo(0f, 0f)
                            lineTo(size.width, 0f)
                            lineTo(size.width, size.height)
                            lineTo(0f, size.height)
                            close()
                        })
                        .clickable {
                            val intent = Intent(context, ClickRecipe::class.java).apply {
                                putExtra("recipeTitle", recipe.title)
                                putExtra("recipeDescription", recipe.description)
                                putExtra("recipeAuthor", recipe.author)
                                putExtra("recipeUID", recipe.UID)
                                putExtra("recipeImage", recipe.image)
                            }
                            context.startActivity(intent)
                        }
                )

            }
        }
    }


