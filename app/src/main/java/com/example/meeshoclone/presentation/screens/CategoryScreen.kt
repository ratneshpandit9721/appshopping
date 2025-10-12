package com.example.meeshoclone.presentation.screens



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.meeshoclone.R // Replace with your actual R file import
import com.example.meeshoclone.presentation.navigation.Routes
import kotlinx.coroutines.launch

// --- Data Classes ---
data class Category(val name: String, val iconRes: Int)
data class CategoryProductItem(val imageRes: Int, val name: String, val categoryName: String)
data class CategorySection(val category: Category, val products: List<CategoryProductItem>)



// --- Main Category Screen Composable ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreenUI(navController: NavController) {

    // --- State Management ---
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var selectedCategory by remember { mutableStateOf(categories[0]) }

    val categoryIndexMap = mutableMapOf<String, Int>()
    var itemIndex = 0

    val sections = categories.map{category ->
        categoryIndexMap[category.name] = itemIndex
        val filteredProducts =  allProducts.filter{it.categoryName == category.name}
        itemIndex += 2
        CategorySection(category= category, products = filteredProducts)
    }

    // --- Side Effects ---
    LaunchedEffect(selectedCategory) {
        categoryIndexMap[selectedCategory.name]?.let { index ->
            coroutineScope.launch {
                listState.animateScrollToItem(index)
            }
        }
    }

    // --- UI Layout ---
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CATEGORIES", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { navController.navigate(Routes.SearchBarScreen) }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                    IconButton(onClick = { navController.navigate(Routes.WishlistScreen) }) {
                        Icon(Icons.Default.FavoriteBorder, contentDescription = "Wishlist")
                    }
                    IconButton(onClick = { navController.navigate(Routes.CartScreen) }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        Row(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // **UPDATED SIDEBAR**
            CategorySidebar(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = {
                    selectedCategory = it
                }
            )
            MainContent(
                sections = sections,
                listState = listState,
                modifier = Modifier.weight(1f) // Main content now takes all remaining space
            )
        }
    }
}



@Composable
fun CategoryItem(
    category: Category,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // The selection indicator on the left
        if (isSelected) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(70.dp) // Adjust to your item height
                    .background(
                        color = Color(0xFF28942B), // Green indicator color
                        shape = RoundedCornerShape(topEnd = 50.dp, bottomEnd = 50.dp)
                    )
            )
        } else {
            Spacer(modifier = Modifier.width(4.dp))
        }

        Spacer(modifier = Modifier.width(4.dp))

        // Icon and Text content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f) // Takes all available space in the middle
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF0F0F0)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = category.iconRes),
                    contentDescription = category.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(40.dp)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = category.name,
                textAlign = TextAlign.Center,
                fontSize = 10.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) Color.Black else Color.DarkGray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 14.sp
            )
        }
    }
}

/**
 * The sidebar on the left, displaying a list of categories.
 */
@Composable
fun CategorySidebar(
    categories: List<Category>,
    selectedCategory: Category,
    onCategorySelected: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .width(80.dp) // Fixed width for the new design
            .shadow(elevation = 4.dp)
            .fillMaxHeight()
            .background(Color.White)
    ) {
        items(categories.size) { index ->
            val category = categories[index]
            CategoryItem(
                category = category,
                isSelected = category.name == selectedCategory.name,
                onClick = { onCategorySelected(category) }
            )
        }
    }
}

/**
 * The main content area on the right, displaying products grouped by category.
 */
@Composable
fun MainContent(
    sections: List<CategorySection>,
    listState: LazyListState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxHeight()
            .padding(horizontal = 8.dp),
        contentPadding = PaddingValues(top = 8.dp)
    ) {
        sections.forEach { section ->
            item {
                Text(
                    text = section.category.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                HorizontalDivider(modifier = Modifier.padding(bottom = 12.dp), color = Color.LightGray)
            }
            item {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 800.dp), // Increased max height
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    userScrollEnabled = false
                ) {
                    items(section.products) { product ->
                        CategoryProductCard(product = product)
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryProductCard(product: CategoryProductItem) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { /* Handle product click */ }
    ) {
        Image(
            painter = painterResource(id = product.imageRes),
            contentDescription = product.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .border(1.dp, Color.LightGray)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = product.name,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            maxLines = 2
        )
    }
}

// --- Preview ---
@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun CategoryScreenPreview() {
    val navController = rememberNavController()
    CategoryScreenUI(navController = navController)
}

// --- Dummy Data (from your images) ---
val categories = listOf(
    Category("Popular", R.drawable.women_image),
    Category("Kurti, Saree & Lehenga", R.drawable.women_image),
    Category("Western", R.drawable.women_image),
    Category("Men", R.drawable.women_image),
    Category("Kids", R.drawable.boy),
    Category("Seasonal", R.drawable.women_image),
    Category("Home & Kitchen", R.drawable.women_image),
    Category("Kurta", R.drawable.women_image),
    Category("Shirts", R.drawable.women_image)
)

val allProducts = listOf(
    CategoryProductItem(R.drawable.boy, "Kids wear", "Kids"),
    CategoryProductItem(R.drawable.boy, "Girls wear", "Popular"),
    CategoryProductItem(R.drawable.boy, "Summer Dress", "Popular"),
    CategoryProductItem(R.drawable.boy, "Casual Tee", "Popular"),
    CategoryProductItem(R.drawable.boy, "Mens wear", "Men"),
    CategoryProductItem(R.drawable.boy, "Formal Shirt", "Men"),
    CategoryProductItem(R.drawable.boy, "Kurtis & Dress", "Kurti, Saree & Lehenga"),
    CategoryProductItem(R.drawable.boy, "Women Suits", "Kurti, Saree & Lehenga"),
    CategoryProductItem(R.drawable.boy, "Wedding Dress", "Kurti, Saree & Lehenga"),
    CategoryProductItem(R.drawable.boy, "Party Dress", "Kurti, Saree & Lehenga"),
    CategoryProductItem(R.drawable.boy, "Engagement", "Kurti, Saree & Lehenga"),
    CategoryProductItem(R.drawable.boy, "Jeans", "Western"),
    CategoryProductItem(R.drawable.boy, "Tops", "Western"),
    CategoryProductItem(R.drawable.boy, "Shirt", "Men"),
    CategoryProductItem(R.drawable.boy, "Shirts & Pants", "Kids"),
    CategoryProductItem(R.drawable.boy, "Summer", "Seasonal"),
    CategoryProductItem(R.drawable.boy, "Mixer", "Home & Kitchen"),
    CategoryProductItem(R.drawable.boy, "Kurta", "Kurta"),
    CategoryProductItem(R.drawable.boy, "Perfumes", "Shirts")
)