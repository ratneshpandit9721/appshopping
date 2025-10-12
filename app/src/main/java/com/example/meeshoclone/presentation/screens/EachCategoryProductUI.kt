package com.example.meeshoclone.presentation.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.meeshoclone.presentation.navigation.Routes
import com.example.meeshoclone.presentation.screens.utils.ProductItem
import com.example.meeshoclone.presentation.viewModels.ShoppingAppViewModel


// --- Main Composable Function ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EachCategoryProductScreenUI(
    viewModel: ShoppingAppViewModel = hiltViewModel(),
    categoryName: String,
    navController: NavController
) {
    val state = viewModel.getSpecificCategoryItemsState.collectAsStateWithLifecycle()
    val products = state.value.userData ?: emptyList()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    LaunchedEffect(categoryName) {
        viewModel.getSpecificCategoryItems(categoryName)
    }


    // This `when` block handles the UI state before rendering the main scaffold
    when {
        state.value.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        state.value.error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Error: ${state.value.error}")
            }
        }
        products.isEmpty() && !state.value.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "No Products Available")
            }
        }
        else -> {
            // Main UI is displayed within the Scaffold
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = categoryName,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        },
                        scrollBehavior = scrollBehavior
                    )
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    // LazyVerticalGrid efficiently displays a grid of items
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(products) { product ->
                            ProductItem(
                                product = product,
                                onProductClick = {
                                    // Navigate to product details screen on click
                                    navController.navigate(Routes.EachProductDetailsScreen(product.productID))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

