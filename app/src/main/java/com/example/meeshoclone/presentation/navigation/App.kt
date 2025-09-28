package com.example.meeshoclone.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.bottombar.AnimatedBottomBar
import com.google.firebase.auth.FirebaseAuth
import com.example.meeshoclone.R
import com.example.meeshoclone.presentation.LoginScreenUI


data class BottomNavItem(
    val name: String,
    val icon: Painter,
    val unselectedIcon: Painter
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter") //surpress the innerpadding of the Scaffold

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
        ),
    )

    var startScreen = if (firebaseAuth.currentUser == null) {
        SubNavigation.LoginSignUpScreen
    } else {
        SubNavigation.MainHomeScreen

    }


    Scaffold(
        Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars),
        bottomBar = {
            if (shouldShowBottomBar) {
                AnimatedBottomBar(
                    selectedItem = selectedItem,
                    itemSize = BottomNavItems.size,
                    containerColor = Color.White,

                    ) {
                    BottomNavItems.forEachIndexed { index, navigationItem ->
                        NavigationBarItem(
                            //modifier = Modifier.align(alignment = Alignment.Top),
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
                                if (index == selectedItem) {
                                    Text(
                                        text = navigationItem.name,
                                        color = Color.Black,
                                        fontSize = 10.sp
                                    )
                                } else {
                                    Text(
                                        text = navigationItem.name,
                                        color = Color.Gray,
                                        fontSize = 10.sp

                                    )
                                }
                            },
                            icon = {
                                Icon(
                                    painter = navigationItem.icon,
                                    contentDescription = navigationItem.name,
                                    modifier = Modifier.size(24.dp),
                                    tint = if (index == selectedItem) {
                                        Color.Black
                                    } else Color.Gray
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
    ) { innerpadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = if (shouldShowBottomBar) 45.dp else 0.dp)
        ) {
            NavHost(navController = navController, startDestination = startScreen) {

                navigation<SubNavigation.LoginSignUpScreen>(startDestination = Routes.LoginScreen) {
                    composable<Routes.LoginScreen> {
                        LoginScreenUi(
                            navController = navController
                        )
                    }
                    composable<Routes.SignUpScreen> {
                        SignUpScreenUI(
                            navController = navController
                        )
                    }
                }
                navigation<SubNavigation.MainHomeScreen>(startDestination = Routes.HomeScreen) {
                    composable<Routes.HomeScreen> {
                        HomeScreenUI(
                            navController = navController
                        )
                    }
                    composable<Routes.CategoryScreen> {
                        CategoryScreenUI(
                            navController = navController
                        )
                    }
                    composable<Routes.MeeshoMallScreen> {
                        MeeshoMallScreenUI(
                            navController = navController
                        )
                    }
                    composable<Routes.MyOrderScreen> {
                        MyOrderScreenUI(
                            navController = navController
                        )
                    }
                    composable<Routes.ProfileScreen> {
                        ProfileScreenUI(
                            navController = navController
                        )
                    }
                    composable<Routes.WishlistScreen> {
                        WishlistScreenUI(
                            navController = navController
                        )
                    }
                    composable<Routes.CartScreen> {
                        CartScreenUI(
                            navController = navController
                        )
                    }
                    composable<Routes.SearchBarScreen> {
                        SearchBarScreenUI(
                            navController = navController
                        )
                    }
                    composable<Routes.AllCategoriesScreen> {
                        AllCategoriesScreenUI(
                            navController = navController
                        )
                    }
                    composable<Routes.PayScreen> {
                        PayScreenUI(
                            navController = navController
                        )

                    }
                    composable<Routes.SeeAllProductsScreen> {
                        GetAllProducts(
                            navController = navController
                        )
                    }
                }
                composable<Routes.EachProductDetailsScreen> {
                    val product: Routes.EachProductDetailsScreen = it.toRoute()
                    EachProductDetailsScreen(
                        navController = navController,
                        productId = product.productID
                    )

                }

                composable<Routes.EachCategoryItemsScreen> {
                    val category: Routes.EachCategoryItemsScreen = it.toRoute()
                    EachCategoryProductScreen(
                        navController = navController,
                        categoryName = category.categoryName
                    )

                }

                composable<Routes.CheckoutScreen> {
                    val product: Routes.CheckoutScreen = it.toRoute()
                    CheckoutScreen(
                        navController = navController,
                        productId = product.productID,
                        payTest = payTest


                    )

                }


            }
        }


    }


}