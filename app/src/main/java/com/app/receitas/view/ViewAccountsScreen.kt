package com.app.receitas.view

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.receitas.R
import com.app.receitas.view.navigations.MainActivity
import com.app.receitas.view.ui.theme.ReceitasTheme
import com.app.receitas.view.ui.theme.YellowButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ViewAccountsScreen : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        auth = Firebase.auth
        setContent {
            ReceitasTheme {
                AccountScreen(this)
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser

        if (currentUser != null) {
            redirectToAnotherActivity()
        }
    }

    private fun redirectToAnotherActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}

@Composable
fun AccountScreen(activity: ComponentActivity) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Image background
        Image(
            painter = painterResource(id = R.drawable.your_recipe_main), // substitua pelo seu recurso de imagem
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 456.dp)
        ) {
            Column {
                Button(
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(YellowButton, Color.White),
                    modifier = Modifier.width(320.dp).height(55.dp),
                    onClick = {
                        val intent = Intent(activity, ViewSignUp::class.java)
                        activity.startActivity(intent)
                    }
                ) {
                    Text(text = "Email and Password")
                }
            }
        }
    }
}
@Preview
@Composable
fun PreviewAccountScreen() {
    ReceitasTheme {
        AccountScreen(ComponentActivity())
    }
}
