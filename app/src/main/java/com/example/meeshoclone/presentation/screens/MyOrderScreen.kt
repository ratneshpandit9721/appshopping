package com.example.meeshoclone.presentation.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
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
fun MyOrderScreenUI(
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "MY ORDERS",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            HorizontalDivider(
                color = Color.LightGray,
                thickness = 0.5.dp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = "",
                    onValueChange = { /* TODO: Implement search functionality */ },
                    modifier = Modifier.padding(vertical = 10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.LightGray
                    ),
                    placeholder = { Text(text = "Search") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null
                        )
                    }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.filter), // Replace with your actual drawable
                    contentDescription = "Filter Icon",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(24.dp),
                    tint = colorResource(R.color.magenta) // Replace with your actual color
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = "Filters",
                    fontSize = 16.sp,
                    color = colorResource(R.color.magenta), // Replace with your actual color
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(50.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.packing), // Replace with your actual drawable
                    contentDescription = "Package"
                )

                Text(
                    text = "No Orders Yet",
                    modifier = Modifier.padding(vertical = 10.dp),
                    fontSize = 26.sp,
                    color = colorResource(R.color.magenta), // Replace with your actual color
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderPreview() { // Renamed Preview function to avoid conflict with potential data classes
    MyOrderScreenUI(navController = rememberNavController())
}

