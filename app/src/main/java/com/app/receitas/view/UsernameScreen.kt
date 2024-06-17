package com.app.receitas.view

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.receitas.R
import com.app.receitas.view.ui.theme.ReceitasTheme
import com.app.receitas.view.ui.theme.YellowButton
import com.app.receitas.viewModel.SignUpViewModel

class UsernameScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, true)

        setContent {
            ReceitasTheme {
                usernameScreenSignUp()
            }
        }
    }
}

@Composable
private fun usernameScreenSignUp() {
    val viewModel: SignUpViewModel = viewModel()
    val context = LocalContext.current as ComponentActivity

    val (usernameValue, setUsernameValue) = remember { mutableStateOf("") }

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

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(26.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .width(320.dp)
                        .background(Color.White),
                    value = usernameValue,
                    onValueChange = setUsernameValue,
                    label = { Text("Username") },
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                shape = RoundedCornerShape(0.dp, 0.dp, 0.dp, 0.dp),
                colors = ButtonDefaults.buttonColors(YellowButton, Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp),
                onClick = {
                    viewModel.username(context, usernameValue)
                },
            ) {
                Text("Sign Up")
            }
        }
    }
}

@Preview
@Composable
private fun usernamePreview() {
    ReceitasTheme {
        usernameScreenSignUp()
    }
}
