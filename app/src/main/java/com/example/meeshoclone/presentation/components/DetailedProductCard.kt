package com.example.meeshoclone.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
// You need to import TextDecoration for the strikethrough effect
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// If you are using colorResource, you need this import.
// I have replaced it with a standard Color for safety.
// import androidx.compose.ui.res.colorResource


// Data Class (Correct)
data class DetailedProductItem(
    val image : Int, // Resource ID for the image
    val productName : String,
    val productPrice : String, // Original price (strikethrough)
    val discountedPrice : String, // Selling price
    val discount : String, // Discount percentage
    val rating : String, // Numerical rating
    val ratingCount : String // Number of ratings/reviews
)

// This is the New Product card used in Home Screen after segmented button
@Composable
fun DetailedProductCard(
    product: DetailedProductItem,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(), // Apply the passed-in modifier, and ensure it fills width
        shape = RoundedCornerShape(4.dp), // Use a slight curve for better visuals
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // 1. Image and Wishlist Icon
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    // Set a fixed aspect ratio or height for images to keep a uniform grid
                    .height(200.dp)
            ) {
                Image(
                    painter = painterResource(product.image),
                    contentDescription = "Product Image: ${product.productName}",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                IconButton(
                    onClick = { /* Handle wishlist toggle */ },
                    modifier = Modifier
                        .padding(8.dp)
                        // Replaced colorResource(R.color.gray) with a standard white/light gray color
                        .background(
                            color = Color.White.copy(alpha = 0.85f),
                            shape = CircleShape
                        )
                        .size(32.dp) // Set a size for the button area
                        .align(Alignment.TopEnd)
                ) {
                    Icon(
                        // Replaced R.drawable.heart1 with Material Icon for demonstration
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Wishlist",
                        tint = Color.Black,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                // 2. Product Name
                Text(
                    text = product.productName,
                    color = Color.DarkGray,
                    fontSize = 13.sp,
                    maxLines = 1, // Restrict to one line
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                // 3. Price Details
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = product.discountedPrice,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = product.productPrice,
                        fontSize = 13.sp,
                        color = Color.Gray,
                        textDecoration = TextDecoration.LineThrough // Correct usage
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = product.discount,
                        color = Color(0xFF00A300), // Custom green for discount
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 4. "Discount Applied" Tag (Correctly placed and styled)
                Row(
                    modifier = Modifier
                        // Removed the empty Box and wrapped the content directly
                        .background(
                            color = Color.Green.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 4.dp, vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Discount applied",
                        fontSize = 10.sp,
                        color = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        // Replaced R.drawable.heart1 with Material Icon for demonstration
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = Color.DarkGray,
                        modifier = Modifier.size(8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                // 5. Delivery Info
                Text(
                    text = "Free Delivery",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(6.dp))

                // 6. Rating and Rating Count
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Rating Chip
                    Row(
                        modifier = Modifier
                            .background(
                                color = Color(0xFF00A300), // Dark Green Background
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 4.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = product.rating,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Icon(
                            // Replaced R.drawable.star with Material Icon for demonstration
                            imageVector = Icons.Default.Star,
                            modifier = Modifier.size(10.dp),
                            tint = Color.White,
                            contentDescription = null
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    // Rating Count
                    Text(
                        text = "(${product.ratingCount})",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}


