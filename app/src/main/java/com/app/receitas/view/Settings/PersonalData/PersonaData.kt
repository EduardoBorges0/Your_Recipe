package com.app.receitas.view.Settings.PersonalData

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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.app.receitas.R
import com.app.receitas.view.Settings.PersonalData.ui.theme.ReceitasTheme
import com.app.receitas.view.ViewAccountsScreen
import com.app.receitas.view.ui.theme.YellowButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PersonaData : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, true)

        setContent {
            ReceitasTheme {
              PersonaDataComposable()
            }
        }
    }
}

@Composable
private fun PersonaDataComposable() {
    val context = LocalContext.current
    val (openDialog, setOpenDialog) = remember { mutableStateOf(false) }

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
                    val intent = Intent(context, EmailSettings::class.java)
                    context.startActivity(intent)
                },
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(YellowButton, Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(55.dp)
            ) {
                Text(text = "Email")
            }
            Button(
                onClick = {
                    val intent = Intent(context, PasswordSettings::class.java)
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(YellowButton, Color.White),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(55.dp)
            ) {
                Text(text = "Senha")
            }
            Button(
                onClick = {
                    setOpenDialog(true)
                },
                colors = ButtonDefaults.buttonColors(YellowButton, Color.White),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(55.dp)
            ) {
                Text(text = "Sair da conta")
            }
            if (openDialog) {
                AlertDialog(
                    onDismissRequest = { setOpenDialog(false) },
                    confirmButton = {
                        Button(
                            onClick = {
                                val intent = Intent(context, ViewAccountsScreen::class.java)
                                context.startActivity(intent)
                                Firebase.auth.signOut()
                            },
                            colors = ButtonDefaults.buttonColors(YellowButton, Color.White)
                        ) {
                            Text(text = "Confirmar")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                setOpenDialog(false)
                            },
                            colors = ButtonDefaults.buttonColors(YellowButton, Color.White)
                        ) {
                            Text(text = "Cancelar")
                        }
                    },
                    title = {
                        Text(text = "Sair da conta")
                    },
                    text = {
                        Text(text = "Deseja sair da conta? Você pode retornar quando quiser")
                    },
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier.padding(18.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PersonaDataPreview(){
    ReceitasTheme {
        PersonaDataComposable()
    }
}