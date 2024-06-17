package com.app.receitas.view.navigations

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.app.receitas.R
import com.app.receitas.models.PostApi.PostRestApi
import com.app.receitas.models.PostApi.RepositoriesPost
import com.app.receitas.models.Recipe
import com.app.receitas.models.Repositories
import com.app.receitas.models.RestAPI
import com.app.receitas.view.detailsPost.TitleRecipe
import com.app.receitas.view.navigations.ui.theme.ReceitasTheme
import com.app.receitas.view.navigations.ui.theme.YellowButton
import com.app.receitas.viewModel.api.MainViewModelApi
import com.app.receitas.viewModel.api.PostViewModelApi
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay

private lateinit var viewModel: MainViewModelApi
private lateinit var postViewModelApi: PostViewModelApi
private lateinit var repositories: Repositories
fun String.capitalizeFirstLetter(): String {
    return this.lowercase().replaceFirstChar {
        if (it.isLowerCase()) it.titlecase() else it.toString()
    }
}

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewModel = MainViewModelApi(Repositories(RestAPI.getInstance()))
        viewModel.getAllRecipes()

        setContent {
            ReceitasTheme {
                val items = listOf(
                    BottomNavigationItem("Home", Icons.Filled.Home, Icons.Outlined.Home),
                    BottomNavigationItem("Search", Icons.Filled.Search, Icons.Outlined.Search),
                    BottomNavigationItem("Settings", Icons.Filled.Settings, Icons.Outlined.Settings),
                    BottomNavigationItem("User", Icons.Filled.AccountCircle, Icons.Outlined.AccountCircle)

                )

                var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            items.forEachIndexed { index, item ->
                                NavigationBarItem(
                                    selected = selectedItemIndex == index,
                                    onClick = {
                                        selectedItemIndex = index
                                        when (index) {
                                            0 -> navController.navigate("home")
                                            1 -> navController.navigate("search")
                                            2 -> navController.navigate("settings")
                                            3 -> navController.navigate("user")
                                        }
                                    },
                                    label = { Text(text = item.title) },
                                    icon = {
                                        Icon(
                                            imageVector = if (index == selectedItemIndex) {
                                                item.selectedIcon
                                            } else {
                                                item.unselectedIcon
                                            },
                                            contentDescription = item.title
                                        )
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(navController = navController, startDestination = "home", modifier = Modifier.padding(innerPadding)) {
                        composable("home") { MainScreen(viewModel) }
                        composable("search") { SearchView(viewModel) }
                        composable("settings") { SettingsViewComposable() }
                        composable("user") { AccountUserComposable(viewModel) }
                    }
                }
            }
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun MainScreen(viewModel: MainViewModelApi) {
    val context = LocalContext.current as ComponentActivity
    val recipes by viewModel.recipes.observeAsState(initial = listOf())
    postViewModelApi = PostViewModelApi(RepositoriesPost(PostRestApi.getInstance()))
    var refreshBoolean = false
    var refreshing by remember { mutableStateOf(false) }
    LaunchedEffect(refreshing) {
        if (refreshing) {
            delay(3000)
            refreshing = false
            refreshBoolean = true
                if (refreshBoolean) {
                    viewModel.getAllRecipes()
                }
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = refreshing),
        onRefresh = {
            refreshing = true
        }
    ) {
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

            LazyColumn(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                items(recipes) { recipe ->
                    RecipeItem(recipe = recipe)
                }
            }

            Box(
                contentAlignment = Alignment.BottomEnd,
                modifier = Modifier.fillMaxSize()
            ) {
                ElevatedButton(
                    shape = RoundedCornerShape(22.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = YellowButton,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .padding(16.dp)
                        .width(64.dp)
                        .height(64.dp),
                    onClick = {
                        val intent = Intent(context, TitleRecipe::class.java)
                        context.startActivity(intent)
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_add_24),
                        contentDescription = "Add Recipe",
                        modifier = Modifier.size(100.dp)
                    )
                }
            }
        }
    }
}

    @Composable
    fun RecipeItem(recipe: Recipe) {
        val context = LocalContext.current as ComponentActivity
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 35.dp, horizontal = 28.dp)
                .clickable {
                    val intent = Intent(context, ClickRecipe::class.java).apply {
                        putExtra("recipeTitle", recipe.title)
                        putExtra("recipeDescription", recipe.description)
                        putExtra("recipeAuthor", recipe.author)
                        putExtra("recipeUID", recipe.UID)
                        putExtra("recipeImage", recipe.image)
                    }
                    context.startActivity(intent)
                },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = recipe.image),
                contentDescription = "Recipe Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp, 8.dp, 0.dp, 0.dp)),
                contentScale = ContentScale.FillWidth
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clip(RoundedCornerShape(0.dp, 0.dp, 8.dp, 8.dp))
                    .background(YellowButton)
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
            ) {
                Text(
                    text = recipe.title.capitalizeFirstLetter(),
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(horizontal = 30.dp).padding(bottom = 5.dp)
                )
                Text(text = "By ${recipe.author}", color = Color.White, fontSize = 14.sp)
            }
        }
    }


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    ReceitasTheme {
        MainScreen(viewModel)
    }
}
