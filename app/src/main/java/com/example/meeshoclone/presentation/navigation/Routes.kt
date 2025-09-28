package com.example.meeshoclone.presentation.navigation

import kotlinx.serialization.Serializable


sealed class SubNavigation {
    @Serializable

    object LoginSignUpScreen : SubNavigation()

    @Serializable

    object  MainHomeScreen : SubNavigation()

}

sealed class Routes {
    @Serializable
    object LoginScreen

    @Serializable
    object SignUpScreen

    @Serializable
    object HomeScreen

    @Serializable
    object CategoryScreen

    @Serializable
    object MeeshoMallScreen

    @Serializable
    object MyOrderScreen

    @Serializable
    object ProfileScreen

    @Serializable
    object SearchBarScreen

    @Serializable
    object WishlistScreen

    @Serializable
    object CartScreen
    @Serializable
    object SeeAllProductsScreen


    @Serializable

    data class CheckoutScreen(val productID: String)

    @Serializable
    object PayScreen

    @Serializable

    object AllCategoriesScreen

    @Serializable
    data class EachCategoryItemsScreen(val categoryName: String)

    @Serializable
    data class EachProductDetailsScreen(val productID: String)



}



