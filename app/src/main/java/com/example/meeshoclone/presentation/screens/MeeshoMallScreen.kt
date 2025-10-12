package com.example.meeshoclone.presentation.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.meeshoclone.presentation.components.DetailedProductItem
import com.example.meeshoclone.presentation.viewModels.ShoppingAppViewModel
import com.example.meeshoclone.R
import com.example.meeshoclone.presentation.components.DetailedProductCard
import com.example.meeshoclone.presentation.navigation.Routes
import com.example.meeshoclone.presentation.screens.utils.Banner

// Note: Some imports and component definitions like 'DetailedProductItem',
// 'Banner', 'DetailedProductCard', and 'Routes' are assumed to exist
// elsewhere in the project.

/**
 * This is the Mall Screen Ui
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeeshoMallScreenUI(
    navController: NavController,
    viewModel: ShoppingAppViewModel = hiltViewModel()
) {
    val homeState by viewModel.homeScreenState.collectAsStateWithLifecycle()
    var selectedIndex by remember { mutableIntStateOf(0) }
    val options = listOf("Sort", "Category", "Gender", "Filters")

    val productItems = listOf(
        DetailedProductItem(
            image = R.drawable.boy,
            productName = "Kids Wear",
            productPrice = "289",
            discountedPrice = "248",
            discount = "20% Off",
            rating = "4.2",
            ratingCount = "(12,707)"
        ),
        DetailedProductItem(
            image = R.drawable.boy,
            productName = "Kids Wear",
            productPrice = "289",
            discountedPrice = "248",
            discount = "20% Off",
            rating = "4.2",
            ratingCount = "(12,707)"
        ),
        DetailedProductItem(
            image = R.drawable.boy,
            productName = "Kids Wear",
            productPrice = "289",
            discountedPrice = "248",
            discount = "20% Off",
            rating = "4.2",
            ratingCount = "(12,707)"
        )
    )

    Scaffold(
        topBar = {
            // Top app bar
            TopAppBar(
                title = {
                    Text(
                        text = "MEESHO MALL",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Routes.SearchBarScreen) }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            modifier = Modifier.size(26.dp),
                            contentDescription = "Search"
                        )
                    }
                    IconButton(onClick = { navController.navigate(Routes.WishlistScreen) }) {
                        Icon(
                            painter = painterResource(R.drawable.heart1),
                            modifier = Modifier.size(22.dp),
                            contentDescription = "Wishlist"
                        )
                    }
                    IconButton(onClick = { navController.navigate(Routes.CartScreen) }) {
                        Icon(
                            painter = painterResource(R.drawable.cart),
                            contentDescription = "Cart",
                            modifier = Modifier.size(22.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(Color.White)
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                Column(modifier = Modifier.fillMaxWidth().background(Color.White)) {
                    homeState.banner?.let { banners ->
                        Banner(banners = banners)
                    }
                }
            }
            item {
                Column(modifier = Modifier.fillMaxWidth().background(colorResource(R.color.light_yellow))) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Budget Buys", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.weight(1f))
                        Text(text = "View All", color = Color.Red)
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(R.drawable.arrowright),
                                modifier = Modifier
                                    .size(16.dp)
                                    .background(color = Color.Red, shape = CircleShape),
                                contentDescription = "View All Products"
                            )
                        }
                    }
                    LazyRow(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(productItems) { product ->
                            DetailedProductCard(
                                product = product,
                                modifier = Modifier.width(160.dp) // set fixed width for each card
                            )
                        }
                    }
                }
            }
            item {
                SingleChoiceSegmentedButtonRow(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    options.forEachIndexed { index, label ->
                        SegmentedButton(
                            modifier = Modifier.background(color = Color.White),
                            shape = RoundedCornerShape(0.dp),
                            onClick = { selectedIndex = index },
                            selected = index == selectedIndex,
                            label = { Text(text = label) }
                        )
                    }
                }
            }
            items(productItems.chunked(2)) { rowItems ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    rowItems.forEach { product ->
                        DetailedProductCard(
                            product = product,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    // If odd number of items, add empty space
                    if (rowItems.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

