package com.app.receitas.view.navigations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.app.receitas.R
import com.app.receitas.view.navigations.ui.theme.ReceitasTheme
import com.app.receitas.view.ui.theme.YellowButton
import com.app.receitas.viewModel.SignUpViewModel

class ChangePasswordViewSignUp : ComponentActivity() {
    private lateinit var viewModel: SignUpViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewModel = SignUpViewModel()
        WindowCompat.setDecorFitsSystemWindows(window, true)

        setContent {
            ReceitasTheme {
                ChangePasswordViewSignUpComposable(viewModel)
            }
        }
    }
}

@Composable
fun ChangePasswordViewSignUpComposable(viewModel: SignUpViewModel){
    val context = LocalContext.current as ComponentActivity
    val (emailValue, setEmailValue) = remember { mutableStateOf("") }
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
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
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
                    if(emailValue.isEmpty()){
                        showAlertDialog.value = true
                    }else{
                        viewModel.ChangePassword(context, emailValue)
                    }


                },
            ) {
                Text("Sign Up")
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
