//package com.example.meeshoclone.presentation
//
//import android.widget.Toast
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import com.example.meeshoclone.R
//
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Email
//import androidx.compose.material.icons.filled.Lock
//import androidx.compose.material.icons.filled.Person
//import androidx.compose.material.icons.filled.Phone
//import androidx.compose.material3.Button
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.HorizontalDivider
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedButton
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.vector.addPathNodes
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import androidx.navigation.NavController
//import com.example.meeshoclone.domain.models.UserData
//import com.example.meeshoclone.presentation.navigation.Routes
//import com.example.meeshoclone.presentation.navigation.SubNavigation
//import com.example.meeshoclone.presentation.screens.utils.SuccessAlertDialog
//import com.example.meeshoclone.presentation.viewModels.ShoppingAppViewModel
//
//
//
//
//@Composable
//
//fun SignUpScreenUI(
//    viewModel: ShoppingAppViewModel = hiltViewModel(),
//    navController: NavController
//) {
//
//    val state = viewModel.signupScreenState.collectAsStateWithLifecycle()
//
//    val context = LocalContext.current
//    if (state.value.isLoading) {
//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
//        }
//    } else if (state.value.error != null) {
//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(text = state.value.error)
//        }
//    } else if (state.value.userData != null) {
//        SuccessAlertDialog(
//            onClick = {
//                navController.navigate(SubNavigation.MainHomeScreen)
//            }
//        )
//
//
//    } else {
//
//
//        val firstName by remember { mutableStateOf("") }
//        val lastName by remember { mutableStateOf("") }
//        val password by remember { mutableStateOf("") }
//        val confirmPassword by remember { mutableStateOf("") }
//        val phoneNumber by remember { mutableStateOf("") }
//        val email by remember { mutableStateOf("") }
//
//
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//
//
//            ) {
//
//            Text(
//                text = "Signup",
//                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 48.sp),
//                fontFamily = FontFamily.Cursive,
//                color = MaterialTheme.colorScheme.primary,
//                modifier = Modifier
//                    .padding(vertical = 16.dp)
//                    .align(Alignment.CenterHorizontally)
//            )
//
//            CustomTextField(
//                value = firstName,
//                onValueChange = { firstName },
//                label = "First Name",
//                leadingIcon = Icons.Default.Person,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 4.dp)
//
//            )
//            CustomTextField(
//                value = lastName,
//                onValueChange = { lastName },
//                label = "Last Name",
//                leadingIcon = Icons.Default.Person,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 4.dp),
//
//                )
//            CustomTextField(
//                value = email,
//                onValueChange = { email },
//                label = "Email",
//                leadingIcon = Icons.Default.Email,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 4.dp)
//
//            )
//            CustomTextField(
//                value = phoneNumber,
//                onValueChange = { phoneNumber },
//                label = "Phone Number",
//                leadingIcon = Icons.Default.Phone,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 4.dp)
//
//            )
//            CustomTextField(
//                value = password,
//                onValueChange = { password },
//                label = "Create Password",
//                leadingIcon = Icons.Default.Lock,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 4.dp),
//                visualTransformation = PasswordVisualTransformation(),
//
//                )
//            CustomTextField(
//                value = confirmPassword,
//                onValueChange = { confirmPassword },
//                label = "Confirm Password",
//                leadingIcon = Icons.Default.Lock,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 4.dp),
//                visualTransformation = PasswordVisualTransformation()
//
//            )
//            Button(
//                onClick = {
//                    if (firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() &&
//                        phoneNumber.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()
//                    ) {
//                        if (password == confirmPassword) {
//
//                            val userData = UserData(
//                                firstName = firstName,
//                                lastName = lastName,
//                                email = email,
//                                password = password,
//                                phoneNumber= phoneNumber)
//
//                            viewModel.createUserData(
//                                userData
//                            )
//
//                            Toast.makeText(context, "Signup Successfully", Toast.LENGTH_SHORT)
//                                .show()
//                        } else {
//
//
//                            Toast.makeText(
//                                context,
//                                "Password and Confirm Password do not match",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    } else {
//
//
//                        Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT)
//                            .show()
//                    }
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 60.dp, vertical = 16.dp),
//                shape = RoundedCornerShape(8.dp),
//                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
//                    containerColor = MaterialTheme.colorScheme.primary,
//                    contentColor = Color.White
//                )
//
//            ) {
//                Text(
//                    text = "SignUp", color = Color.White,
//                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
//                    fontFamily = FontFamily.Cursive,
//
//
//                    )
//
//            }
//
//            Row(modifier = Modifier.padding(), verticalAlignment = Alignment.CenterVertically) {
//                Text(
//                    text = "Already have an account?", color = Color.Black,
//                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 14.sp),
//                )
//                TextButton(
//                    onClick = {
//                        navController.navigate(Routes.LoginScreen)
//                    },
//                    contentPadding = PaddingValues(0.dp)
//                ) {
//                    Text(
//                        text = "Login", color = MaterialTheme.colorScheme.primary,
//                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 14.sp),
//                        modifier = Modifier.padding(start = 4.dp),
//
//
//                        )
//                }
//
//            }
//
//            Row(
//                modifier = Modifier.padding(vertical = 6.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//
//                HorizontalDivider(modifier = Modifier.weight(1f))
//                Text(
//                    text = "Or",
//                    color = Color.Black,
//                    modifier = Modifier.padding(horizontal = 8.dp)
//                )
//                HorizontalDivider(modifier = Modifier.weight(1f))
//
//
//            }
//
//            OutlinedButton(
//                onClick = {},
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp, horizontal = 60.dp),
//                shape = RoundedCornerShape(8.dp),
//            ) {
//
//                Image(
//                    painter = painterResource(id = R.drawable.googler), contentDescription = null,
//                    modifier = Modifier.size(24.dp)
//                )
//
//                Spacer(modifier = Modifier.size(8.dp))
//                Text(
//                    text = "Login with Google", color = Color.Black,
//                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
//                    fontFamily = FontFamily.Cursive,
//                    modifier = Modifier.padding(start = 8.dp)
//                )
//
//
//            }
//
//
//        }
//
//
//    }
//}
//
//
//



package com.example.meeshoclone.presentation // Or your screen's package

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.meeshoclone.domain.models.UserData
import com.example.meeshoclone.presentation.navigation.Routes
import com.example.meeshoclone.presentation.viewModels.ShoppingAppViewModel

// This is the main, stateful composable. It handles state and logic.
@Composable
fun SignUpScreenUI(
    viewModel: ShoppingAppViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.signupScreenState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    // This effect handles navigation automatically on successful sign-up
    LaunchedEffect(state.userData) {
        if (state.userData != null) {
            Toast.makeText(context, "Registration Successful! Please log in.", Toast.LENGTH_LONG).show()
            // Navigate to the Login screen after successful sign-up
            navController.navigate(Routes.LoginScreen) {
                // Clear the back stack to prevent going back to sign-up
                popUpTo(navController.graph.id) { inclusive = true }
            }
        }
    }

    // This effect shows a Toast for any errors
    LaunchedEffect(state.error) {
        if (state.error.isNotBlank()) {
            Toast.makeText(context, state.error, Toast.LENGTH_LONG).show()
        }
    }

    // The UI is managed by a stateless composable
    SignUpScreenContent(
        isLoading = state.isLoading,
        onSignUpClick = { fullName, email, password ->
            // Send the sign-up event to the ViewModel
            val userData = UserData(firstName = fullName, email = email, password = password)
            viewModel.createUserData(userData)
        },
        onLoginClick = {
            // Navigate back to the login screen
            navController.popBackStack()
        }
    )
}

// This is a stateless composable. It only displays UI and sends events up.
@Composable
private fun SignUpScreenContent(
    isLoading: Boolean,
    onSignUpClick: (String, String, String) -> Unit,
    onLoginClick: () -> Unit
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.displayMedium.copy(fontFamily = FontFamily.Cursive),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Full Name Field
            CustomTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = "Full Name",
                leadingIcon = Icons.Default.Person,
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            )

            // Email Field
            CustomTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email",
                leadingIcon = Icons.Default.Email,
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            )

            // Password Field
            CustomTextField(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                leadingIcon = Icons.Default.Lock,
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            )

            // Confirm Password Field
            CustomTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "Confirm Password",
                leadingIcon = Icons.Default.Lock,
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    // Simple client-side validation before calling the ViewModel
                    if (fullName.isBlank() || email.isBlank() || password.isBlank()) {
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    } else if (password != confirmPassword) {
                        Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                    } else {
                        onSignUpClick(fullName, email, password)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp),
                shape = RoundedCornerShape(8.dp),
                enabled = !isLoading
            ) {
                Text(
                    text = "Create Account",
                    style = MaterialTheme.typography.titleLarge.copy(fontFamily = FontFamily.Cursive)
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "Already have an account?",
                    style = MaterialTheme.typography.bodyMedium
                )
                TextButton(onClick = onLoginClick) {
                    Text("Log In", color = MaterialTheme.colorScheme.primary)
                }
            }
        }

        // Show a loading indicator overlay if isLoading is true
        if (isLoading) {
            CircularProgressIndicator()
        }
    }
}
