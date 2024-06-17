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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private lateinit var recipe : com.app.receitas.models.Recipe
class TitleRecipe : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, true)
        recipe = com.app.receitas.models.Recipe("", "", "", "", "")
        setContent {
            ReceitasTheme {
                TitleRecipeComposable(recipe)
            }
        }
    }
}

@Composable
private fun TitleRecipeComposable(recipe: com.app.receitas.models.Recipe) {
    val context = LocalContext.current
    val (titleValue, setTitleValue) = remember {
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
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f)) // Spacer to push the content towards center
            TextField(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .width(320.dp)
                    .background(Color.White),
                value = titleValue,
                onValueChange = setTitleValue,
                label = { Text("Qual o nome da sua receita?") },
            )
            Spacer(modifier = Modifier.weight(1f)) // Spacer to push the content towards center and button to bottom
            Button(
                shape = RoundedCornerShape(0.dp, 0.dp, 0.dp, 0.dp),
                colors = ButtonDefaults.buttonColors(YellowButton, Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp),
                onClick = {
                      if(titleValue.isEmpty()){

                      }else if(recipe.UID == Firebase.auth.currentUser?.uid){
                          if (titleValue == recipe.title){

                          }
                      }
                      else{
                          val intent = Intent(context, Recipe::class.java).apply {
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


