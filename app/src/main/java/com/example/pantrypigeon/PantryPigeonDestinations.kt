package com.example.pantrypigeon

interface PantryPigeonDestinations {
    val route: String
}

object Home : PantryPigeonDestinations {
    override val route = "homeView"
}

object AddProduct : PantryPigeonDestinations {
     override val route = "addProductView"
}