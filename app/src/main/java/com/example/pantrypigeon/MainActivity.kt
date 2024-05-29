package com.example.pantrypigeon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pantrypigeon.addProduct.AddProductScreen
import com.example.pantrypigeon.home.HomeView
import com.example.pantrypigeon.home.HomeViewModel
import com.example.pantrypigeon.pantry.PantryView
import com.example.pantrypigeon.pantry.PantryViewModel
import com.example.pantrypigeon.productDetails.ProductDetailView
import com.example.pantrypigeon.productDetails.ProductDetailsViewModel
import com.example.pantrypigeon.ui.theme.PantryPigeonTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val productViewModel: ProductViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private val productDetails: ProductDetailsViewModel by viewModels()
    private val pantryViewModel: PantryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PantryPigeonTheme {
                val state by productViewModel.state.collectAsState()
                val stateProductDetails by productDetails.stateProductDetails.collectAsState()
                val stateOldestProduct by homeViewModel.oldestProductState.collectAsState()
                val stateNewestProduct by pantryViewModel.newestProductState.collectAsState()

                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = HomeView.route
                ) {
                    composable(route = HomeView.route) {
                        HomeView(
                            onClickAddProduct = {
                                navController.navigateSingleTopTo(
                                    AddProductView.route
                                )
                            },
                            navPantry = { navController.navigateSingleTopTo(PantryView.route) },
                            state = stateOldestProduct
                        )
                    }
                    composable(route = PantryView.route) {
                        PantryView(
                            state = stateNewestProduct,
                            navProductDetailView = {
                                navController.navigateSingleTopTo(
                                    ProductDetailView.route
                                )
                            },
                            getProductDetailsById = productDetails::getProductDetailsById
                        )
                    }
                    composable(route = ProductDetailView.route) {
                        ProductDetailView(
                            stateProductDetails = stateProductDetails,
                            onEvent = productViewModel::onEvent,
                            naveToPantry = { navController.navigateSingleTopTo(PantryView.route) }
                        )
                    }

                    composable(route = AddProductView.route) {
                        AddProductScreen(
                            state = state,
                            onEvent = productViewModel::onEvent,
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