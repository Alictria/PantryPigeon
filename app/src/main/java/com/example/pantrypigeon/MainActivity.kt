package com.example.pantrypigeon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pantrypigeon.ui.AddProductScreen
import com.example.pantrypigeon.ui.HomeView
import com.example.pantrypigeon.ui.theme.PantryPigeonTheme

class MainActivity : ComponentActivity(
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PantryPigeon()
        }
    }


    @Preview
    @Composable
    fun PantryPigeon() {
        PantryPigeonTheme {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = Home.route
            ) {
                composable(route = Home.route) {
                    HomeView(onClickAddProduct = { navController.navigateSingleTopTo(AddProduct.route) })
                }
                composable(route = AddProduct.route) {
                    AddProductScreen(this@MainActivity, onClickSaveProduct = {
                    navController.navigateSingleTopTo(Home.route)
                    })
                }
            }
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) { launchSingleTop = true }