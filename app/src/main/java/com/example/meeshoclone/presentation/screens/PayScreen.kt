package com.example.meeshoclone.presentation.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.meeshoclone.R // Assuming your R file is in this package

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PayScreenUI(navController: NavController) {
    // A Scaffold is assumed to be the root, as 'innerPadding' is used.

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            // Product Details Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(150.dp, 150.dp)
                        .padding(horizontal = 12.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.women_image), // Replace with your actual drawable
                        contentDescription = "women image"
                    )
                }

                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Suit Light full Neck",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    // The rest of the product details from this column were not visible in the image
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Price Details Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Price Details", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Icon(
                    painter = painterResource(R.drawable.arrowright), // Replace with your actual drawable
                    tint = Color.Black,
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Total Product Price", color = Color.Gray, fontSize = 14.sp)
                Text(text = "Rs. 10000", color = Color.Gray, fontSize = 14.sp)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Total Discount", color = Color.Green, fontSize = 14.sp)
                Text(text = "- Rs. 599", color = Color.Gray, fontSize = 14.sp)
            }

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                color = Color.Gray,
                thickness = 0.5.dp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Order Total", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = "Rs. 9401", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
@Preview
fun Pay() {
    PayScreenUI(navController = rememberNavController())
}

