package com.app.receitas.view.detailsPost

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.app.receitas.R
import com.app.receitas.view.detailsPost.ui.theme.ReceitasTheme
import com.app.receitas.view.ui.theme.YellowButton

class Recipe : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, true)
        val titleValue = intent?.getStringExtra("titleValue") ?: ""

        setContent {
            ReceitasTheme {
                RecipeComposable(titleValue)
            }
        }
    }
}

@Composable
fun RecipeComposable(titleValue : String) {
    val context = LocalContext.current as ComponentActivity
    val (recipeValue, setRecipeValue) = remember {
        mutableStateOf("")
    }
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
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .width(320.dp)
                    .height(594.dp)
                    .padding(top = 56.dp)
                    .background(Color.White),
                value = recipeValue,
                onValueChange = setRecipeValue,
                label = { Text("Qual a receita? Exemplo: 4 xicaras de farinha, etc...") },
            )
            Spacer(modifier = Modifier.weight(1f)) // Spacer to push the content towards center and button to bottom
            Button(
                shape = RoundedCornerShape(0.dp),
                colors = ButtonDefaults.buttonColors(YellowButton, Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp),
                onClick = {
                    if (recipeValue.isEmpty()) {
                        // Faça algo com o valor da receita
                    }
                    else{
                        val intent = Intent(context, ImageRecipe::class.java).apply {
                            putExtra("recipeValue", recipeValue)
                            putExtra("titleValue", titleValue)
                        }
                        context.startActivity(intent)
                    }
                },
            ) {
                Text("Continuar")
            }
        }
    }
}