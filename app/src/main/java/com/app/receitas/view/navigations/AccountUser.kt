package com.app.receitas.view.navigations

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
import androidx.compose.runtime.Composable
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
import com.app.receitas.view.navigations.ui.theme.ReceitasTheme
import com.app.receitas.view.navigations.ui.theme.YellowButton
import com.app.receitas.viewModel.api.MainViewModelApi
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AccountUser : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReceitasTheme {

            }
        }
    }
}

@Composable
fun AccountUserComposable(viewModel: MainViewModelApi) {
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

        // Perfil do usuário
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(YellowButton)
                .padding(top = 30.dp, start = 9.dp, end = 9.dp)
                .height(170.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_account_circle_24),
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                )
                Column {
                    Text(
                        text = "${Firebase.auth.currentUser?.displayName}",
                        modifier = Modifier.padding(start = 10.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                    Text(
                        text = "${Firebase.auth.currentUser?.uid}",
                        modifier = Modifier.padding(start = 10.dp),
                        fontSize = 15.sp,
                        color = Color.White
                    )
                }
            }
        }

        // Lista de receitas
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 200.dp) // Ajuste o valor conforme necessário para posicionar abaixo do perfil
        ) {
            LazyVerticalGrid(
                GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(viewModel.recipes.value ?: listOf()) { recipe ->
                    RecipeItemUser(recipe = recipe)
                }
            }
        }
    }
}

@Composable
fun RecipeItemUser(recipe: Recipe) {
    val context = LocalContext.current as ComponentActivity
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 15.dp, horizontal = 3.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(recipe.UID == Firebase.auth.currentUser?.uid){
            Image(
                painter = rememberAsyncImagePainter(model = recipe.image),
                contentDescription = "image",
                contentScale = ContentScale.Crop, // Ensure the image is cropped to fit the shape
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
