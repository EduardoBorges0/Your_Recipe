package com.app.receitas.view.detailsPost

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import coil.compose.rememberImagePainter
import com.app.receitas.R
import com.app.receitas.models.PostApi.PostRestApi
import com.app.receitas.models.PostApi.RepositoriesPost
import com.app.receitas.models.Repositories
import com.app.receitas.models.RestAPI
import com.app.receitas.view.detailsPost.ui.theme.ReceitasTheme
import com.app.receitas.view.navigations.MainActivity
import com.app.receitas.view.navigations.ui.theme.YellowButton
import com.app.receitas.viewModel.ImageFirebaseViewModel
import com.app.receitas.viewModel.api.MainViewModelApi
import com.app.receitas.viewModel.api.PostViewModelApi
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private lateinit var postViewModelApi: PostViewModelApi
private lateinit var viewModelApi: MainViewModelApi
class ImageRecipe : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, true)
        val titleValue = intent?.getStringExtra("titleValue") ?: ""
        val recipeValue = intent?.getStringExtra("recipeValue") ?: ""

        setContent {
            ReceitasTheme {
                ImageComposable(titleValue, recipeValue)
            }
        }
    }
}

@Composable
private fun ImageComposable(titleValue: String, recipeValue: String) {
    postViewModelApi = PostViewModelApi(RepositoriesPost(PostRestApi.getInstance()))
    viewModelApi = MainViewModelApi(Repositories(RestAPI.getInstance()))
    val viewModel = ImageFirebaseViewModel()
    val context = LocalContext.current as ComponentActivity
    val imageUri = rememberSaveable { mutableStateOf<Uri?>(null) }
    val imageUrl = rememberSaveable { mutableStateOf<String?>(null) }
    val isLoading = rememberSaveable { mutableStateOf(false) } // Estado para controlar o carregamento

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                imageUri.value = it
                isLoading.value = true // Ativa o estado de carregamento
                viewModel.uploadImageToFirebase(it) { url ->
                    imageUrl.value = url
                    isLoading.value = false // Desativa o estado de carregamento quando terminar
                }
            }
        }
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.your_recipe),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(horizontal = 30.dp)
        ) {
            if (imageUrl.value != null) {
                // Mostra a imagem carregada
                Image(
                    painter = rememberImagePainter(imageUrl.value!!),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.FillWidth
                )
            }

            Button(
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(YellowButton, Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(55.dp),
                onClick = {
                    launcher.launch("image/*")
                },
                enabled = !isLoading.value
            ) {
                Text(text = "Escolher imagem")
            }
        }

        if (isLoading.value) {
            // Tela de carregamento (CircularProgressIndicator) no centro
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = YellowButton)
            }
        }

        Button(
            shape = RoundedCornerShape(0.dp, 0.dp, 0.dp, 0.dp),
            colors = ButtonDefaults.buttonColors(com.app.receitas.view.ui.theme.YellowButton, Color.White),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(65.dp),
            onClick = {
                val intent = Intent(context, MainActivity::class.java).apply {
                    postViewModelApi = PostViewModelApi(RepositoriesPost(PostRestApi.getInstance()))
                    postViewModelApi.postRecipe(
                        titleValue,
                        recipeValue,
                        Firebase.auth.currentUser?.displayName.toString(),
                        imageUrl.value ?: "",
                        Firebase.auth.currentUser?.uid.toString()
                    )
                    postViewModelApi = PostViewModelApi(RepositoriesPost(PostRestApi.getInstance()))
                    postViewModelApi.postResult.observe(context) { postResult ->
                        if (postResult) {
                            viewModelApi.getAllRecipes()
                        }
                    }
                }
                context.startActivity(intent)
            },
            enabled = !isLoading.value
        ) {
            Text("Confirmar")
        }
    }
}
