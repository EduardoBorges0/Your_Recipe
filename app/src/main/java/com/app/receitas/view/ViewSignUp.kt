package com.app.receitas.view

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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.receitas.R
import com.app.receitas.view.navigations.ChangePasswordViewSignUp
import com.app.receitas.view.ui.theme.ReceitasTheme
import com.app.receitas.view.ui.theme.YellowButton
import com.app.receitas.viewModel.SignUpViewModel

class ViewSignUp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            ReceitasTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SignUp()
                }
            }
        }
    }
}

@Composable
fun SignUp() {
    val viewModel: SignUpViewModel = viewModel()
    val context = LocalContext.current as ComponentActivity
    val (passwordVisible, setPasswordVisible) = remember { mutableStateOf(false) }

    val (emailValue, setEmailValue) = remember { mutableStateOf("") }
    val (passwordValue, setPasswordValue) = remember { mutableStateOf("") }
    val showAlertDialog = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.your_recipe),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .blur(100.dp)
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
                        .width(320.dp),
                    value = emailValue,
                    onValueChange = setEmailValue,
                    label = { Text("Email") },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent
                    )
                )
                TextField(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .width(320.dp),
                    value = passwordValue,
                    onValueChange = setPasswordValue,
                    label = { Text("Password") },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (passwordVisible) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24
                        IconButton(onClick = { setPasswordVisible(!passwordVisible) }) {
                            Icon(painter = painterResource(id = image), contentDescription = "Toggle password visibility")
                        }
                    },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                Column (
                    modifier = Modifier
                        .padding(horizontal = 50.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(YellowButton)
                        .width(180.dp)
                        .height(40.dp)
                        .align(Alignment.Start)
                        .clickable {
                            val intent = Intent(context, ChangePasswordViewSignUp::class.java)
                            context.startActivity(intent)
                        },
                ){
                    Text(
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .padding(top = 6.dp),
                        text = "Esqueceu a senha?"
                    )
                }

            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                shape = RoundedCornerShape(0.dp, 0.dp, 0.dp, 0.dp),
                colors = ButtonDefaults.buttonColors(YellowButton, Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp),
                onClick = {
                    if (emailValue.isEmpty() || passwordValue.isEmpty()) {
                        showAlertDialog.value = true
                    } else {
                        viewModel.signUpOrSignIn(activity = context, emailValue, passwordValue)
                    }
                },
            ) {
                Text("Sign Up")
            }

            if (showAlertDialog.value) {
                AlertDialog(
                    onDismissRequest = { showAlertDialog.value = false },
                    title = { Text("Erro") },
                    text = { Text("Por favor, preencha todos os campos.") },
                    confirmButton = {
                        Button(onClick = { showAlertDialog.value = false }) {
                            Text("OK")
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpPreview() {
    ReceitasTheme {
        SignUp()
    }
}
