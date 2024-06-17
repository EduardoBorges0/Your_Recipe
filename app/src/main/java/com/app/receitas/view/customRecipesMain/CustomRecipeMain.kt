package com.app.receitas.view.customRecipesMain

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.receitas.R
import com.app.receitas.view.customRecipesMain.ui.theme.ReceitasTheme

class CustomRecipeMain : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReceitasTheme {
                 CustomRecipe()
            }
        }
    }
}

@Composable
fun CustomRecipe(){
    Column {
        Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = "fdfv")
        Row (
            modifier = Modifier.padding(vertical = 8.dp) // Adiciona espaçamento vertical
        ){
            Text(
                text = "title",
                modifier = Modifier
                    .weight(0.2f) // Ocupa 50% da largura da linha
                    .padding(end = 8.dp) // Adiciona espaçamento à direita
            )
            Text(
                text = "author",
                modifier = Modifier
                    .weight(1f) // Ocupa 50% da largura da linha
                    .padding(start = 8.dp) // Adiciona espaçamento à esquerda
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomPreview(){
    com.app.receitas.view.ui.theme.ReceitasTheme {
        CustomRecipe()
    }
}