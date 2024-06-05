package com.example.pantrypigeon

interface PantryPigeonDestinations {
    val route: String
}

object Home : PantryPigeonDestinations {
    override val route = "home"
}

object AddProduct : PantryPigeonDestinations {
    override val route = "addProduct"
}

object Pantry : PantryPigeonDestinations {
    override val route = "pantry"
}

object ProductDetail : PantryPigeonDestinations {
    override val route = "productDetail"
}

object RecipeSuggestion : PantryPigeonDestinations {
    override val route = "recipeSuggestion"
}