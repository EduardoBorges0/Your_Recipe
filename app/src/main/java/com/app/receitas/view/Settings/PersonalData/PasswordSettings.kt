package com.app.receitas.view.Settings.PersonalData

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.receitas.R
import com.app.receitas.view.Settings.PersonalData.ui.theme.ReceitasTheme
import com.app.receitas.view.ui.theme.YellowButton
import com.app.receitas.viewModel.SignUpViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PasswordSettings : ComponentActivity() {
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReceitasTheme {
                PasswordComposable(viewModel)
            }
        }
    }
}

@Composable
fun PasswordComposable(viewModel: SignUpViewModel) {
    val context = LocalContext.current as ComponentActivity
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
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(20.dp, 60.dp, 20.dp)) {
            Text(
                text = "Dados de acesso",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Caso você não lembre a sua senha de acesso ao your recipes, fique a vontade para mudar.",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 13.sp
                ),
            )
            Button(
                onClick = {
                    viewModel.ChangePassword(context, Firebase.auth.currentUser?.email.toString()) // Substitua pelo e-mail real do usuário
                },
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(YellowButton, Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
                    .height(55.dp)

            ) {
                Text(text = "Atualize sua senha!")
            }
        }

        if (viewModel.showAlertDialog.value) {
            AlertDialog(

                onDismissRequest = { viewModel.showAlertDialog.value = false },
                title = { Text("Aviso") },
                text = { Text(viewModel.alertDialogMessage.value) },
                confirmButton = {

                    Button(colors = ButtonDefaults.buttonColors(YellowButton, Color.White),
                        onClick = { viewModel.showAlertDialog.value = false }) {
                        Text("OK")
                    }

                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewPassword() {
    ReceitasTheme {
        PasswordComposable(SignUpViewModel())
    }
}
