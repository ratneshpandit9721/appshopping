package com.example.meeshoclone.presentation.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.meeshoclone.R // Replace with your actual R file import
import com.example.meeshoclone.domain.models.CartDataModels
import com.example.meeshoclone.presentation.navigation.Routes
import com.example.meeshoclone.presentation.viewModels.ShoppingAppViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/*
--- Placeholder Classes for Compilation ---
Replace these with your actual backend models and ViewModel.
*/



// --- Main Composable Function ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EachProductDetailsScreenUi(
    viewModel: ShoppingAppViewModel = hiltViewModel(),
    navController: NavController,
    productId: String
) {
    val getProductById = viewModel.getProductByIDState.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val context = LocalContext.current
    // UI State
    var selectedSize by remember { mutableStateOf("M") }
    var quantity by remember { mutableIntStateOf(1) }
    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        viewModel.getProductByID(productId)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Routes.SearchBarScreen) }) {
                        Icon(painterResource(R.drawable.search), "Search", modifier = Modifier.size(28.dp))
                    }
                    IconButton(onClick = { /* Handle notification click */ }) {
                        Icon(painterResource(R.drawable.bell), "Notification", modifier = Modifier.size(22.dp))
                    }
                    IconButton(onClick = { navController.navigate(Routes.CartScreen) }) {
                        Icon(painterResource(R.drawable.cart), "Cart", modifier = Modifier.size(22.dp))
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        when {
            getProductById.value.isLoading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            getProductById.value.error != null -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = getProductById.value.error!!)
                }
            }
            getProductById.value.userData != null -> {
                val product = getProductById.value.userData!!
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Product Image, Details, Size, and Quantity selectors...
                    // (Code from previous step remains unchanged here)

                    Box(modifier = Modifier.height(300.dp)) {
                        AsyncImage(
                            model = product.image,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = product.category,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Rs ${product.price}",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        Text("Size", style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf("S", "M", "L", "XL").forEach { size ->
                                OutlinedButton(
                                    onClick = { selectedSize = size },
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        containerColor = if (selectedSize == size) MaterialTheme.colorScheme.primary else Color.Transparent,
                                        contentColor = if (selectedSize == size) Color.White else MaterialTheme.colorScheme.primary
                                    )
                                ) { Text(size) }
                            }
                        }
                        Text("Quantity", style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))
                        Row(
                            modifier = Modifier.background(colorResource(R.color.light_magenta), RoundedCornerShape(16.dp)).padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            IconButton(onClick = { if (quantity > 1) quantity-- }) {
                                Text("-", style = MaterialTheme.typography.headlineSmall, fontSize = 36.sp, color = Color.White)
                            }
                            Text(quantity.toString(), style = MaterialTheme.typography.bodyLarge, fontSize = 20.sp, color = Color.White)
                            IconButton(onClick = { quantity++ }) {
                                Text("+", style = MaterialTheme.typography.headlineSmall, color = Color.White)
                            }
                        }
                        Text("Description", style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))
                        Text( product.description, style = MaterialTheme.typography.bodyMedium)

                        Spacer(modifier = Modifier.height(24.dp))

                        val magentaColor = colorResource(R.color.magenta)

                        // --- MODIFIED SECTION STARTS HERE ---

                        Button(
                            onClick = {
                                // **UPDATED** - Now creates the full data model
                                val cartDataModels = CartDataModels(
                                    category = product.category,
                                    image = product.image,
                                    price = product.price,
                                    finalPrice = product.finalPrice,
                                    availableunits = product.availableunits,
                                    quantity = quantity,
                                    size = selectedSize,
                                    productID = product.productID,
                                    description = product.description

                                )
                                viewModel.addToCart(cartDataModels)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = magentaColor)
                        ) {
                            Text("Add to Cart")
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = { navController.navigate(Routes.CheckoutScreen(productId)) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = magentaColor)
                        ) {
                            Text("Buy Now")
                        }

                        // **UPDATED** - Favorite button is now an OutlinedButton with a Row
                        OutlinedButton(
                            onClick = {
                                isFavorite = !isFavorite
                                viewModel.addToFav(product)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = "Favorite"
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Add to Wishlist")
                            }
                        }
                        // --- MODIFIED SECTION ENDS HERE ---
                    }
                }
            }
        }
    }
}

