package com.app.receitas.view.navigations

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.app.receitas.R
import com.app.receitas.view.Settings.PersonalData.AboutView
import com.app.receitas.view.Settings.PersonalData.PersonaData
import com.app.receitas.view.navigations.ui.theme.ReceitasTheme
import com.app.receitas.view.ui.theme.YellowButton

class SettingsView : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReceitasTheme {
                SettingsViewComposable()
            }
        }
    }
}

@Composable
fun SettingsViewComposable() {
    SettingsComposable()
}

@Composable
private fun SettingsComposable() {
    val context = LocalContext.current
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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    val intent = Intent(context, PersonaData::class.java)
                    context.startActivity(intent)
                },
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(YellowButton, Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(55.dp)
            )
            {
                Text(text = "Dados Pessoais")
            }
            Button(
                onClick = {
                          val intent = Intent(context, AboutView::class.java)
                    context.startActivity(intent)

                },
                colors = ButtonDefaults.buttonColors(YellowButton, Color.White),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(55.dp)
            )
            {
                Text(text = "Sobre")
            }
        }
    }
}
