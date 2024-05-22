package com.example.pantrypigeon

import ProductViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.pantrypigeon.ui.AddProductScreen
import com.example.pantrypigeon.ui.HomeView
import com.example.pantrypigeon.ui.PantryView
import com.example.pantrypigeon.ui.ProductDetailView
import com.example.pantrypigeon.ui.theme.PantryPigeonTheme

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            ProductDatabase::class.java,
            "products.db"
        ).build()
    }

    private val viewModel by viewModels<ProductViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ProductViewModel(db.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PantryPigeonTheme {
                val state by viewModel.state.collectAsState()
                val stateProductDetails by viewModel.stateProductDetails.collectAsState()

                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = HomeView.route
                ) {
                    composable(route = HomeView.route) {
                        HomeView(onClickAddProduct = {
                            navController.navigateSingleTopTo(
                                AddProductView.route
                            )
                        }, navPantry = { navController.navigateSingleTopTo(PantryView.route) })
                    }
                    composable(route = PantryView.route) {
                        PantryView(
                            state = state,
                            navProductDetailView = {
                                navController.navigateSingleTopTo(
                                    ProductDetailView.route
                                )
                            },
                            getProductDetailsById = viewModel::getProductDetailsById
                        )
                    }
                    composable(route = ProductDetailView.route) {
                        ProductDetailView(
                            stateProductDetails = stateProductDetails
                        )
                    }

                    composable(route = AddProductView.route) {
                        AddProductScreen(
                            state = state,
                            onEvent = viewModel::onEvent,
                            navBack = {
                                navController.navigateSingleTopTo(
                                    HomeView.route
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) { launchSingleTop = true }