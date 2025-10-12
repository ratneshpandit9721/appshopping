package com.example.meeshoclone.presentation.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.meeshoclone.R // Assuming R file is in this package

// This is the search bar screen which comes after clicking on Search Bar
@Composable
fun SearchBarScreenUI(
    navController: NavController
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.arrowback),
                        contentDescription = "back button"
                    )
                }
                SearchBarComponentForSearchScreen()
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp),
                color = Color.LightGray,
                thickness = 0.5.dp
            )

            Column(modifier = Modifier.fillMaxWidth().background(Color.White)) {
                Text(
                    text = "Popular Searches",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                ) {
                    OutlinedCardForSearchScreen(product = "saree")
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedCardForSearchScreen(product = "kurti")
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedCardForSearchScreen(product = "short kurti")
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedCardForSearchScreen(product = "tshirt")
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedCardForSearchScreen(product = "top for women")
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    OutlinedCardForSearchScreen(product = "kurti set")
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedCardForSearchScreen(product = "watch")
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedCardForSearchScreen(product = "earing") // Assuming typo correction from "earring"
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedCardForSearchScreen(product = "top")
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .padding(bottom = 12.dp)
                ) {
                    OutlinedCardForSearchScreen(product = "slipper")
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedCardForSearchScreen(product = "bangle")
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedCardForSearchScreen(product = "tshirt for women")
                }

                Image(
                    painter = painterResource(R.drawable.search),
                    contentDescription = null
                )
            }
        }
    }
}

/**
 * Placeholder for the custom SearchBarComponent.
 */
@Composable
fun SearchBarComponentForSearchScreen() {
    // Implement your search bar UI here
    Text(
        text = "Search Component...",
        modifier = Modifier.padding(16.dp)
    )
}

/**
 * Placeholder for the custom OutlinedCard.
 */
@Composable
fun OutlinedCardForSearchScreen(product: String) {
    // Implement your outlined card UI here
    Text(
        text = product,
        modifier = Modifier.padding(8.dp)
    )
}


@Composable
@Preview(showBackground = true)
fun SearchScreenPreview() { // Renamed for clarity to avoid conflict
    SearchBarScreenUI(navController = rememberNavController())
}

