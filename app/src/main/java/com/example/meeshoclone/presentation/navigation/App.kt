package com.example.meeshoclone.presentation.navigation


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.*
import androidx.navigation.toRoute
import com.example.bottombar.AnimatedBottomBar
import com.google.firebase.auth.FirebaseAuth
import com.example.meeshoclone.R
import com.example.meeshoclone.presentation.*
import com.example.meeshoclone.presentation.screens.*

data class BottomNavItem(
    val name: String,
    val icon: Painter,
    val unselectedIcon: Painter
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun App(firebaseAuth: FirebaseAuth, payTest: () -> Unit) {

    val navController = rememberNavController()
    var selectedItem by remember { mutableIntStateOf(0) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    var shouldShowBottomBar by remember { mutableStateOf(false) }

    LaunchedEffect(currentDestination) {
        shouldShowBottomBar = when (currentDestination) {
            Routes.HomeScreen::class.qualifiedName,
            Routes.CategoryScreen::class.qualifiedName,
            Routes.MyOrderScreen::class.qualifiedName,
            Routes.MeeshoMallScreen::class.qualifiedName -> true
            else -> false
        }
    }

    val BottomNavItems = listOf(
        BottomNavItem(
            "Home",
            icon = painterResource(R.drawable.home),
            unselectedIcon = painterResource(R.drawable.home)
        ),
        BottomNavItem(
            "Categories",
            icon = painterResource(R.drawable.categories),
            unselectedIcon = painterResource(R.drawable.categories)
        ),
        BottomNavItem(
            "My Orders",
            icon = painterResource(R.drawable.my_orders),
            unselectedIcon = painterResource(R.drawable.my_orders)
        ),
        BottomNavItem(
            "Mall",
            icon = painterResource(R.drawable.mall),
            unselectedIcon = painterResource(R.drawable.mall)
        )
    )

    // Dynamic start screen logic
    val startDestination = if (firebaseAuth.currentUser == null) "auth_graph" else "main_graph"

    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars),
        bottomBar = {
            if (shouldShowBottomBar) {
                AnimatedBottomBar(
                    selectedItem = selectedItem,
                    itemSize = BottomNavItems.size,
                    containerColor = Color.White
                ) {
                    BottomNavItems.forEachIndexed { index, navigationItem ->
                        NavigationBarItem(
                            selected = selectedItem == index,
                            onClick = {
                                selectedItem = index
                                when (index) {
                                    0 -> navController.navigate(Routes.HomeScreen)
                                    1 -> navController.navigate(Routes.CategoryScreen)
                                    2 -> navController.navigate(Routes.MyOrderScreen)
                                    3 -> navController.navigate(Routes.MeeshoMallScreen)
                                }
                            },
                            label = {
                                Text(
                                    text = navigationItem.name,
                                    color = if (index == selectedItem) Color.Black else Color.Gray,
                                    fontSize = 10.sp
                                )
                            },
                            icon = {
                                Icon(
                                    painter = navigationItem.icon,
                                    contentDescription = navigationItem.name,
                                    modifier = Modifier.size(24.dp),
                                    tint = if (index == selectedItem) Color.Black else Color.Gray
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = if (shouldShowBottomBar) 45.dp else 0.dp)
        ) {
            NavHost(
                navController = navController,
                startDestination = startDestination
            ) {
                // Authentication Graph
                navigation(
                    startDestination = Routes.LoginScreen::class.qualifiedName!!,
                    route = "auth_graph"
                ) {
                    composable<Routes.LoginScreen> {
                        LoginScreenUi(navController = navController)
                    }
                    composable<Routes.SignUpScreen> {
                        SignUpScreenUI(navController = navController)
                    }
                }

                // Main App Graph
                navigation(
                    startDestination = Routes.HomeScreen::class.qualifiedName!!,
                    route = "main_graph"
                ) {
                    composable<Routes.HomeScreen> {
                        HomeScreenUI(navController = navController)
                    }
                    composable<Routes.CategoryScreen> {
                        CategoryScreenUI(navController = navController)
                    }
                    composable<Routes.MeeshoMallScreen> {
                        MeeshoMallScreenUI(navController = navController)
                    }
                    composable<Routes.MyOrderScreen> {
                        MyOrderScreenUI(navController = navController)
                    }
                    composable<Routes.ProfileScreen> {
                        ProfileScreenUI(
                            navController = navController,
                            firebaseAuth = firebaseAuth
                        )
                    }
                    composable<Routes.WishlistScreen> {
                        WishlistScreenUI(navController = navController)
                    }
                    composable<Routes.CartScreen> {
                        CartScreenUI(navController = navController)
                    }
                    composable<Routes.SearchBarScreen> {
                        SearchBarScreenUI(navController = navController)
                    }
                    composable<Routes.AllCategoriesScreen> {
                        AllCategoriesScreenUi(navController = navController)
                    }
                    composable<Routes.PayScreen> {
                        PayScreenUI(navController = navController)
                    }
                    composable<Routes.SeeAllProductsScreen> {
                        GetAllProductsUI(navController = navController)
                    }
                }

                // Additional independent routes
                composable<Routes.EachProductDetailsScreen> {
                    val product: Routes.EachProductDetailsScreen = it.toRoute()
                    EachProductDetailsScreenUi(
                        navController = navController,
                        productId = product.productID
                    )
                }

                composable<Routes.EachCategoryItemsScreen> {
                    val category: Routes.EachCategoryItemsScreen = it.toRoute()
                    EachCategoryProductScreenUI(
                        navController = navController,
                        categoryName = category.categoryName
                    )
                }

                composable<Routes.CheckoutScreen> {
                    val product: Routes.CheckoutScreen = it.toRoute()
                    CheckOutScreenUI(
                        navController = navController,
                        productId = product.productID,
                        pay = payTest
                    )
                }
            }
        }
    }
}






//
//package com.example.meeshoclone.presentation.navigation
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.painter.Painter
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavGraph.Companion.findStartDestination
//import androidx.navigation.compose.*
//import androidx.navigation.toRoute
//import com.example.bottombar.AnimatedBottomBar
//import com.google.firebase.auth.FirebaseAuth
//import com.example.meeshoclone.R
//import com.example.meeshoclone.presentation.*
//import com.example.meeshoclone.presentation.screens.*
//
//// Simplified data class with a route property
//data class BottomNavItem(
//    val name: String,
//    val icon: Painter,
//    val route: Any // Use 'Any' to accommodate different route types
//)
//
//@Composable
//fun App(firebaseAuth: FirebaseAuth, payTest: () -> Unit) {
//
//    val navController = rememberNavController()
//    val navBackStackEntry by navController.currentBackStackEntryAsState()
//    val currentRoute = navBackStackEntry?.destination?.route
//
//    val bottomNavItems = remember {
//        listOf(
//            BottomNavItem("Home", painterResource(R.drawable.home), Routes.HomeScreen),
//            BottomNavItem("Categories", painterResource(R.drawable.categories), Routes.CategoryScreen),
//            BottomNavItem("My Orders", painterResource(R.drawable.my_orders), Routes.MyOrderScreen),
//            BottomNavItem("Mall", painterResource(R.drawable.mall), Routes.MeeshoMallScreen)
//        )
//    }
//
//    // Determine if the bottom bar should be shown based on the current route
//    val shouldShowBottomBar = bottomNavItems.any { it.route::class.qualifiedName == currentRoute }
//
//    val startDestination = if (firebaseAuth.currentUser == null) "auth_graph" else "main_graph"
//
//    Scaffold(
//        modifier = Modifier.fillMaxSize(),
//        bottomBar = {
//            if (shouldShowBottomBar) {
//                NavigationBar(containerColor = Color.White) {
//                    bottomNavItems.forEach { item ->
//                        val isSelected = item.route::class.qualifiedName == currentRoute
//                        NavigationBarItem(
//                            selected = isSelected,
//                            onClick = {
//                                navController.navigate(item.route) {
//                                    // Pop up to the start destination to avoid building a large back stack
//                                    popUpTo(navController.graph.findStartDestination().id) {
//                                        saveState = true
//                                    }
//                                    // Avoid multiple copies of the same destination when re-selecting the same item
//                                    launchSingleTop = true
//                                    // Restore state when re-selecting a previously selected item
//                                    restoreState = true
//                                }
//                            },
//                            label = {
//                                Text(
//                                    text = item.name,
//                                    color = if (isSelected) Color.Black else Color.Gray,
//                                    fontSize = 10.sp
//                                )
//                            },
//                            icon = {
//                                Icon(
//                                    painter = item.icon,
//                                    contentDescription = item.name,
//                                    modifier = Modifier.size(24.dp),
//                                    tint = if (isSelected) Color.Black else Color.Gray
//                                )
//                            },
//                            colors = NavigationBarItemDefaults.colors(
//                                indicatorColor = Color.Transparent
//                            )
//                        )
//                    }
//                }
//            }
//        }
//    ) { innerPadding -> // <-- Use the provided innerPadding
//        NavHost(
//            navController = navController,
//            startDestination = startDestination,
//            modifier = Modifier.padding(innerPadding) // <-- Apply it here
//        ) {
//            // Authentication Graph
//            navigation(
//                startDestination = Routes.LoginScreen::class.qualifiedName!!,
//                route = "auth_graph"
//            ) {
//                composable<Routes.LoginScreen> { LoginScreenUi(navController = navController) }
//                composable<Routes.SignUpScreen> { SignUpScreenUI(navController = navController) }
//            }
//
//            // Main App Graph
//            navigation(
//                startDestination = Routes.HomeScreen::class.qualifiedName!!,
//                route = "main_graph"
//            ) {
//                composable<Routes.HomeScreen> { HomeScreenUI(navController = navController) }
//                composable<Routes.CategoryScreen> { CategoryScreenUI(navController = navController) }
//                composable<Routes.MeeshoMallScreen> { MeeshoMallScreenUI(navController = navController) }
//                composable<Routes.MyOrderScreen> { MyOrderScreenUI(navController = navController) }
//                composable<Routes.ProfileScreen> { ProfileScreenUI(navController = navController, firebaseAuth = firebaseAuth) }
//                composable<Routes.WishlistScreen> { WishlistScreenUI(navController = navController) }
//                composable<Routes.CartScreen> { CartScreenUI(navController = navController) }
//                composable<Routes.SearchBarScreen> { SearchBarScreenUI(navController = navController) }
//                composable<Routes.AllCategoriesScreen> { AllCategoriesScreenUi(navController = navController) }
//                composable<Routes.PayScreen> { PayScreenUI(navController = navController) }
//                composable<Routes.SeeAllProductsScreen> { GetAllProductsUI(navController = navController) }
//            }
//
//            // Additional independent routes
//            composable<Routes.EachProductDetailsScreen> {
//                val product: Routes.EachProductDetailsScreen = it.toRoute()
//                EachProductDetailsScreenUi(navController = navController, productId = product.productID)
//            }
//
//            composable<Routes.EachCategoryItemsScreen> {
//                val category: Routes.EachCategoryItemsScreen = it.toRoute()
//                EachCategoryProductScreenUI(navController = navController, categoryName = category.categoryName)
//            }
//
//            composable<Routes.CheckoutScreen> {
//                val product: Routes.CheckoutScreen = it.toRoute()
//                CheckOutScreenUI(navController = navController, productId = product.productID, pay = payTest)
//            }
//        }
//    }
//}