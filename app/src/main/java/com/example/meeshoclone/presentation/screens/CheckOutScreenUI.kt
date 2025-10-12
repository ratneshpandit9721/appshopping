package com.example.meeshoclone.presentation.screens



import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource // Required for R.color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.meeshoclone.R // Assuming R.color.magenta is in your project
import com.example.meeshoclone.presentation.navigation.Routes
import com.example.meeshoclone.presentation.viewModels.ShoppingAppViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow



// --- Main Composable Function ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckOutScreenUI(
    viewModel: ShoppingAppViewModel = hiltViewModel(),
    navController: NavController,
    productId: String,
    pay : () -> Unit
) {
    // State collection from ViewModel
    val state = viewModel.getProductByIDState.collectAsState()
    val productData = state.value.userData

    // State for TextFields
    val email = remember { mutableStateOf("") }
    val country = remember { mutableStateOf("") }
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    val city = remember { mutableStateOf("") }
    val postalCode = remember { mutableStateOf("") }

    // State for RadioButtons
    val selectedMethod = remember { mutableStateOf("Standard FREE delivery over Rs.4500") }

    // Fetch product data when the screen is first composed
    LaunchedEffect(key1 = Unit) {
        viewModel.getProductByID(productId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "SHIPPING",
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
                }
            )
        }
    ) { innerPadding ->

        // State-based UI rendering
        when {
            state.value.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            state.value.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Sorry, Unable to Get Information")
                }
            }
            state.value.userData == null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No Products Available")
                }
            }
            else -> {
                // Main content when data is available
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    // Product Summary Section
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(
                            model = state.value.userData!!.image,
                            contentDescription = null,
                            modifier = Modifier
                                .size(80.dp)
                                .border(1.dp, Color.Gray)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = state.value.userData!!.category,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "₹${state.value.userData!!.finalPrice}",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Contact Information Section
                    Column {
                        Text(
                            text = "Contact Information",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = email.value,
                            onValueChange = { email.value = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Email") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Shipping Address Section
                    Column {
                        Text(
                            text = "Shipping Address",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = country.value,
                            onValueChange = { country.value = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Country/Region") }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row {
                            OutlinedTextField(
                                value = firstName.value,
                                onValueChange = { firstName.value = it },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 8.dp),
                                label = { Text("First Name") }
                            )
                            OutlinedTextField(
                                value = lastName.value,
                                onValueChange = { lastName.value = it },
                                modifier = Modifier.weight(1f),
                                label = { Text("Last Name") }
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = address.value,
                            onValueChange = { address.value = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Address") }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row {
                            OutlinedTextField(
                                value = city.value,
                                onValueChange = { city.value = it },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 8.dp),
                                label = { Text("City") }
                            )
                            OutlinedTextField(
                                value = postalCode.value,
                                onValueChange = { postalCode.value = it },
                                modifier = Modifier.weight(1f),
                                label = { Text("Postal Code") }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Shipping Method Section
                    Column {
                        Text(
                            text = "Shipping Method",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        // Option 1
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = selectedMethod.value == "Standard FREE delivery over Rs.4500",
                                    onClick = { selectedMethod.value = "Standard FREE delivery over Rs.4500" }
                                )
                                .padding(vertical = 4.dp)
                        ) {
                            RadioButton(
                                selected = selectedMethod.value == "Standard FREE delivery over Rs.4500",
                                onClick = { selectedMethod.value = "Standard FREE delivery over Rs.4500" }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Standard FREE delivery over Rs.4500")
                        }
                        // Option 2
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = selectedMethod.value == "Cash on delivery Rs.50",
                                    onClick = { selectedMethod.value = "Cash on delivery Rs.50" }
                                )
                                .padding(vertical = 4.dp)
                        ) {
                            RadioButton(
                                selected = selectedMethod.value == "Cash on delivery Rs.50",
                                onClick = { selectedMethod.value = "Cash on delivery Rs.50" }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Cash on delivery Rs.50")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Continue Button with custom color
                    Button(
                        onClick = { navController.navigate(Routes.PayScreen) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            // This uses a direct Color object.
                            // If you have R.color.magenta defined in colors.xml,
                            // you can use colorResource(id = R.color.magenta) instead.
                            containerColor = Color.Magenta
                        )
                    ) {
                        Text("Continue to Shipping")
                    }
                }
            }
        }
    }
}
