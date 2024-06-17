package com.app.receitas.view.Settings.PersonalData

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.receitas.R
import com.app.receitas.view.Settings.PersonalData.ui.theme.ReceitasTheme
import com.app.receitas.view.ui.theme.YellowButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class EmailSettings : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReceitasTheme {
                EmailComposable()
            }
        }
    }
}


@Composable
private fun EmailComposable() {

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Image background
            Image(
                painter = painterResource(id = R.drawable.your_recipe), // substitua pelo seu recurso de imagem
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(modifier = Modifier.fillMaxSize().padding(20.dp, 60.dp, 20.dp)) {
            Text(
                text = "Dados de acesso",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Não é possível alterar seu email, pois é a sua única forma de acesso ao your recipes.",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 13.sp
                ),

                )
                Button(
                    onClick = {  },
                    shape = RoundedCornerShape(4.dp),

                    colors = ButtonDefaults.buttonColors(YellowButton, Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp)
                        .height(55.dp)
                        .then(Modifier.clickable(enabled = false){})


                )
                {
                    Text(text = "${Firebase.auth.currentUser?.email}")
                }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewEmail(){
    ReceitasTheme {
        EmailComposable()
    }
}