package com.example.meeshoclone.presentation.screens

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.meeshoclone.domain.models.ProductDataModels
import com.example.meeshoclone.presentation.components.DetailedProductItem
import com.example.meeshoclone.presentation.components.LowPriceCardItem
import com.example.meeshoclone.presentation.viewModels.ShoppingAppViewModel
import com.example.meeshoclone.R
import com.example.meeshoclone.presentation.components.DetailedProductCard
import com.example.meeshoclone.presentation.components.HorizontalCarousel
import com.example.meeshoclone.presentation.components.LowPriceCard
import com.example.meeshoclone.presentation.components.SearchBarComponentForHomeScreen
import com.example.meeshoclone.presentation.components.TopBarComponent
import com.example.meeshoclone.presentation.navigation.Routes

// Note: Some imports and component definitions like 'DetailedProductItem', 'LowPriceCardItem',
// 'TopBarComponent', 'SearchBarComponentForHomeScreen', 'HorizontalCarousel',
// 'DetailedProductCard', and 'Routes' are assumed to exist elsewhere in the project.

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenUI(
    viewModel: ShoppingAppViewModel = hiltViewModel(),
    navController: NavController
) {
    val homeState by viewModel.homeScreenState.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        snapAnimationSpec = spring(
            stiffness = Spring.StiffnessMedium,
        )
    )

    val listState = rememberLazyListState()
    var selectedIndex by remember { mutableIntStateOf(0) }
    val options = listOf("Sort", "Category", "Gender", "Filters")

    // this is for the suggested for you column
    val getAllSuggestedProductsState =
        viewModel.getAllSuggestedProductState.collectAsStateWithLifecycle()
    val getSuggestedProductData: List<ProductDataModels> =
        getAllSuggestedProductsState.value.userData.orEmpty().filterNotNull()

    LaunchedEffect(key1 = Unit) {
        viewModel.getAllSuggestedProducts()
    }

    if (homeState.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    } else if (homeState.errorMessage != null) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(text = homeState.errorMessage!!)
        }
    } else {

        val items = listOf(
            LowPriceCardItem(image = 1, price = "$299", cardName = "Kids Boys"),
            LowPriceCardItem(image = 1, price = "$299", cardName = "Kids Girls"),
            LowPriceCardItem(image = 1, price = "$299", cardName = "Kids Clothing"),
            LowPriceCardItem(image = 1, price = "$299", cardName = "Jewellery"),
            LowPriceCardItem(image = 1, price = "$299", cardName = "Women Sarees")
        )

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
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    TopBarComponent(scrollBehavior, navController)
                    SearchBarComponentForHomeScreen(navController)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        ) { innerPadding ->
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .background(color = Color.White)
                    .padding(innerPadding)
            ) {
                // Categories Section
                item {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    ) {
                        item {
                            ViewAllCategoryItem(onClick = { navController.navigate(Routes.CategoryScreen) })
                        }
                        items(homeState.categories ?: emptyList()) { category ->
                            CategoryItem(
                                imageUrl = category.categoryimage,
                                category = category.category,
                                onClick = {
                                    navController.navigate(
                                        Routes.EachCategoryItemsScreen(categoryName = category.category)
                                    )
                                }
                            )
                        }
                    }
                }

                item { HorizontalCarousel() }

                // Flash Sale Section
                item {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Flash Sale",
                                fontWeight = FontWeight.Bold,
                                color = Color.DarkGray,
                                fontSize = 20.sp
                            )
                            Text(
                                text = "See more",
                                color = colorResource(id = R.color.orange),
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.clickable { /* Handle click */ })
                        }
                    }
                }

                item {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(homeState.products ?: emptyList()) { product ->
                            ProductCard(product = product, navController = navController)
                        }
                    }
                }

                // Low Price Store Section
                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 16.dp)
                    ) {
                        Text(
                            text = "Low Price Store",
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray,
                            fontSize = 20.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 0.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(items) { cardData ->
                            LowPriceCard(cardData = cardData)
                        }
                    }
                }

                // Filter Buttons Section
                item {
                    SingleChoiceSegmentedButtonRow(
                        modifier = Modifier.padding(
                            horizontal = 16.dp,
                            vertical = 8.dp
                        )
                    ) {
                        options.forEachIndexed { index, label ->
                            SegmentedButton(
                                modifier = Modifier.background(color = Color.White),
                                shape = SegmentedButtonDefaults.itemShape(
                                    index = index,
                                    count = options.size
                                ),
                                onClick = { selectedIndex = index },
                                selected = index == selectedIndex,
                                label = { Text(text = label) }
                            )
                        }
                    }
                }

                // Two-Column Product Grid Section
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
}

/**
 * This is the All Category tab in Category Row
 */
@Composable
fun ViewAllCategoryItem(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(color = colorResource(R.color.gray)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.categories),
                modifier = Modifier.padding(8.dp),
                contentDescription = "View ALL",
                tint = Color.Unspecified
            )
        }
        Text(text = "ALL", style = MaterialTheme.typography.bodyMedium)
    }
}


/**
 * This the category items in showing in Circle
 */
@Composable
fun CategoryItem(
    imageUrl: String,
    category: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(Color.LightGray, CircleShape)
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
        }
        Text(text = category, style = MaterialTheme.typography.bodyMedium)
    }
}

/**
 * it is the Flash sell section Product Card
 */
@Composable
fun ProductCard(product: ProductDataModels, navController: NavController) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .clickable {
                navController.navigate(Routes.EachProductDetailsScreen(productID = product.productID))
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            AsyncImage(
                model = product.image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp) // Set a fixed height
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = product.category,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "$${product.finalPrice}",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$${product.price}",
                        style = MaterialTheme.typography.bodySmall,
                        textDecoration = TextDecoration.LineThrough,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "(${product.availableunits} left)",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}