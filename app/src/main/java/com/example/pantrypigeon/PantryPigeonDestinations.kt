package com.example.pantrypigeon

interface PantryPigeonDestinations {
    val route: String
}

object HomeView : PantryPigeonDestinations {
    override val route = "homeView"
}

object AddProductView : PantryPigeonDestinations {
    override val route = "addProductView"
}