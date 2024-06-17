package com.app.receitas.view.navigations

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import coil.compose.rememberImagePainter
import com.app.receitas.R
import com.app.receitas.models.DeleteRestApi
import com.app.receitas.models.Repositories
import com.app.receitas.models.RestAPI
import com.app.receitas.view.navigations.ui.theme.ReceitasTheme
import com.app.receitas.view.navigations.ui.theme.YellowButton
import com.app.receitas.viewModel.api.MainViewModelApi
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private lateinit var viewModelApi: MainViewModelApi
class ClickRecipe : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, true)
        viewModelApi = MainViewModelApi(Repositories(RestAPI.getInstance()))
        setContent {
            ReceitasTheme {
                val recipeTitle = intent?.getStringExtra("recipeTitle") ?: ""
                val recipeImage = intent?.getStringExtra("recipeImage") ?: ""
                val recipeAuthor = intent?.getStringExtra("recipeAuthor") ?: ""
                val recipeDescription = intent?.getStringExtra("recipeDescription") ?: ""
                val recipeUID = intent?.getStringExtra("recipeUID") ?: ""
                ClickRecipeComposable(recipeTitle, recipeImage, recipeAuthor, recipeDescription, recipeUID, viewModelApi)
            }
        }
    }
}

@Composable
fun ClickRecipeComposable(recipeTitle: String, recipeImage: String, recipeAuthor: String, recipeDescription: String, recipeUID: String, viewModelApi: MainViewModelApi) {
    var openDialog by remember {
        mutableStateOf( false )
    }
    val context = LocalContext.current as ComponentActivity
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.your_recipe),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        val constraints = if (maxHeight < 500.dp) {
            Modifier
        } else {
            Modifier.verticalScroll(rememberScrollState())
        }

        Column(
            modifier = constraints
                .padding(horizontal = 30.dp)
                .padding(top = 70.dp)
        ) {
            Image(
                painter = rememberImagePainter(data = recipeImage),
                contentDescription = "image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.FillWidth
            )
            if(Firebase.auth.currentUser?.uid == recipeUID){
                Column(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(YellowButton)
                        .padding(bottom = 26.dp)
                        .padding(horizontal = 20.dp)
                ) {
                    Button(onClick = {
                        openDialog = true
                    },
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(top = 20.dp),
                        colors = ButtonDefaults.buttonColors(Color.Transparent)
                    ) {
                        Icon(imageVector = Icons.Outlined.Delete, contentDescription = "Icon DELETE")
                    }
                    if(openDialog){
                        AlertDialog(
                            onDismissRequest = {
                                 openDialog = false
                            },
                            confirmButton = {
                                Button(
                                    colors = ButtonDefaults.buttonColors(
                                        YellowButton, Color.White
                                    ),
                                    shape = RoundedCornerShape(13.dp),
                                    onClick = {
                                        CoroutineScope(Dispatchers.IO).launch {
                                            val response = DeleteRestApi.getInstance().deleteRecipe(recipeUID, recipeTitle)
                                            if (response.isSuccessful) {
                                                openDialog = false
                                                val intent = Intent(context, MainActivity::class.java)
                                                context.startActivity(intent)
                                            }
                                        }
                                    })
                                {
                                    Text(text = "Confirmar")
                                }
                            },
                            dismissButton = {
                                Button(
                                    colors = ButtonDefaults.buttonColors(
                                        YellowButton, Color.White
                                    ),
                                    shape = RoundedCornerShape(13.dp),
                                    onClick = {
                                        openDialog = false
                                    })
                                {
                                    Text(text = "Cancelar")
                                }
                            },
                            title = {
                                Text(text = "Remover Receita")
                            },
                            text = {
                                Text(text = "Deseja remover sua receita?")
                            },
                            shape = RoundedCornerShape(18.dp),
                            modifier = Modifier.padding(18.dp)
                        )
                    }
                    
                    Text(
                        text = recipeTitle.capitalizeFirstLetter(),
                        color = Color.White,
                        modifier = Modifier
                            .padding(bottom = 36.dp)
                            .align(Alignment.CenterHorizontally),
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp
                    )
                    Text(
                        text = "Receita",
                        color = Color.White,
                        fontSize = 26.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = recipeDescription,
                        color = Color.White,
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .padding(horizontal = 15.dp)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                            .clickable {
                                val intent = Intent(context, AnotherUserClick::class.java).apply {
                                    putExtra("recipe-UID", recipeUID)
                                    putExtra("recipe-username", recipeAuthor)
                                }
                                context.startActivity(intent)
                            }
                    ) {
                        Row (
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ){
                            Image(painter = painterResource(id = R.drawable.baseline_account_circle_24),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(top = 5.dp)
                                    .padding(horizontal = 4.dp)
                                    .size(20.dp))
                            Text(
                                text = recipeAuthor,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                modifier = Modifier
                            )
                        }

                        Text(
                            text = recipeUID,
                            color = Color.White,
                            fontSize = 13.sp,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }
                }
            }
            else{
                Column(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(YellowButton)
                        .padding(26.dp)
                ) {
                    Text(
                        text = recipeTitle.capitalizeFirstLetter(),
                        color = Color.White,
                        modifier = Modifier
                            .padding(bottom = 36.dp)
                            .align(Alignment.CenterHorizontally),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(
                        text = "Receita",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = recipeDescription,
                        color = Color.White,
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .padding(horizontal = 15.dp)
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                            .clickable {
                                val intent = Intent(context, AnotherUserClick::class.java).apply {
                                    putExtra("recipe-UID", recipeUID)
                                    putExtra("recipe-username", recipeAuthor)
                                }
                                context.startActivity(intent)
                            }
                    ) {
                        Row (
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ){
                            Image(painter = painterResource(id = R.drawable.baseline_account_circle_24),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(top = 5.dp)
                                    .padding(horizontal = 4.dp)
                                    .size(20.dp))
                            Text(
                                text = recipeAuthor,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                modifier = Modifier
                            )
                        }

                        Text(
                            text = recipeUID,
                            color = Color.White,
                            fontSize = 13.sp,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }
                }
            }

        }
    }
}
