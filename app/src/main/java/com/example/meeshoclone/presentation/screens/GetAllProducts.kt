package com.example.meeshoclone.presentation.screens



// Note: Ensure you have the necessary imports for Jetpack Compose, Material 3, Coil, etc.
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.meeshoclone.presentation.navigation.Routes
import com.example.meeshoclone.presentation.viewModels.ShoppingAppViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetAllProductsUI(
    viewModel: ShoppingAppViewModel = hiltViewModel(),
    navController: NavController
) {
    // Collect UI state from the ViewModel
    val getAllProductsState = viewModel.getAllProductState.collectAsStateWithLifecycle()
    val productData = getAllProductsState.value.userData ?: emptyList()

    // Define scroll behavior for the TopAppBar
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    // Trigger data fetching when the screen is first composed
    LaunchedEffect(key1 = Unit) {
        viewModel.getAllProducts()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "All Products",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
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
            // Search field
            OutlinedTextField(
                value = "", // You might want to add a state for this
                onValueChange = { /* Implement search functionality */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Search") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
            )

            // Handle different UI states
            when {
                getAllProductsState.value.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                getAllProductsState.value.error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Sorry, Unable to Get Information")
                    }
                }
                productData.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "No Products Available")
                    }
                }
                else -> {
                    // Display the grid of products
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(productData) { product ->
                            // Assuming ProductItem is a composable similar to the ProductCard
                            // from the previous request.
                            ProductCard(product = product, onProductClick = {
                                // Navigate to product details
                                navController.navigate(Routes.EachProductDetailsScreen(product.productID))
                            })
                        }
                    }
                }
            }
        }
    }
}

