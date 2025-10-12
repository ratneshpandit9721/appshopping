
package com.example.meeshoclone.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.meeshoclone.R
import com.example.meeshoclone.domain.models.UserData
import com.example.meeshoclone.presentation.navigation.Routes
import com.example.meeshoclone.presentation.screens.utils.SuccessAlertDialog
import com.example.meeshoclone.presentation.viewModels.ShoppingAppViewModel




@Composable

fun LoginScreenUi(
    viewModel: ShoppingAppViewModel = hiltViewModel(),
    navController: NavController
) {

    val state = viewModel.loginScreenState.collectAsStateWithLifecycle()
    val showDialog = remember { mutableStateOf(false) }


    val context = LocalContext.current
    if (state.value.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (state.value.error.isNotEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = state.value.error)
        }
    } else if (state.value.userData !=null ) {
        SuccessAlertDialog(
            onClick = {
                navController.navigate(Routes.HomeScreen)
            }
        )


    } else {


        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }



        Column(

            modifier = Modifier
                .fillMaxSize().background(Color.Yellow)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {

            Text(
                text = "Login",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 60.sp),
                fontFamily = FontFamily.Cursive,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )

            CustomTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email",
                leadingIcon = Icons.Default.Email,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)

            )
            CustomTextField(
                value = password,
                onValueChange = {password = it },
                label = "Password",
                leadingIcon = Icons.Default.Lock,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),


                )
            TextButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.align(Alignment.End),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "Forgot Password?",
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp),
                    color = Color.Black,
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 20.dp)

                )
            }
            Button(
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                                viewModel.loginUser(
                                    UserData(
                                        email = email,
                                        password = password
                                    ),
                                )
                            } else {
                                Toast.makeText(
                                    context,
                                    "Please fill all the fields",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp, vertical = 1.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                )

            ) {
                Text(
                    text = "Login", color = Color.White,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                    fontFamily = FontFamily.Cursive,


                    )

            }

            Row(modifier = Modifier.padding(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Do not have an account?", color = Color.Black,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 14.sp),
                )
                TextButton(
                    onClick = {

                                   navController.navigate(Routes.SignUpScreen)

                     }

                ) {
                    Text(
                        text = "SignUp", color = MaterialTheme.colorScheme.primary,
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 14.sp),
                        modifier = Modifier.padding(start = 4.dp),


                        )
                }

            }

            Row(
                modifier = Modifier.padding(vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                HorizontalDivider(modifier = Modifier.weight(1f))
                Text(
                    text = "Or",
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                HorizontalDivider(modifier = Modifier.weight(1f))


            }

            OutlinedButton(
                onClick = {/*todo*/},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 60.dp),
                shape = RoundedCornerShape(8.dp),


            ) {

                Image(
                    painter = painterResource(id = R.drawable.googler), contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "Login with Google", color = Color.Black,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                    fontFamily = FontFamily.Cursive,
                    modifier = Modifier.padding(start = 8.dp)
                )


            }


        }
    }
  }


//
//package com.example.meeshoclone.presentation
//
//import android.widget.Toast
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Email
//import androidx.compose.material.icons.filled.Lock
//import androidx.compose.material3.*
//import androidx.compose.material3.HorizontalDivider
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import androidx.navigation.NavController
//import com.example.meeshoclone.R
//import com.example.meeshoclone.domain.models.UserData
//import com.example.meeshoclone.presentation.navigation.SubNavigation
//import com.example.meeshoclone.presentation.screens.utils.SuccessAlertDialog
//import com.example.meeshoclone.presentation.viewModels.ShoppingAppViewModel
//
//
//@Composable
//fun LoginScreenUi(
//    viewModel: ShoppingAppViewModel = hiltViewModel(),
//    navController: NavController
//) {
//
//    val state = viewModel.loginScreenState.collectAsStateWithLifecycle()
//    val context = LocalContext.current
//
//    var email by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.Yellow),
//        contentAlignment = Alignment.Center
//    ) {
//        when {
//            state.value.isLoading -> {
//                CircularProgressIndicator()
//            }
//
//            state.value.error.isNotEmpty() -> {
//                Toast.makeText(context, state.value.error, Toast.LENGTH_SHORT).show()
//            }
//
//            state.value.userData != null -> {
//                SuccessAlertDialog(
//                    onClick = {
//                        navController.navigate(SubNavigation.MainHomeScreen) {
//                            popUpTo(0) // clear backstack
//                        }
//                    }
//                )
//            }
//
//            else -> {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.Center
//                ) {
//                    Text(
//                        text = "Login",
//                        style = TextStyle(
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 60.sp
//                        ),
//                        fontFamily = FontFamily.Cursive,
//                        color = MaterialTheme.colorScheme.primary,
//                        modifier = Modifier.padding(vertical = 16.dp)
//                    )
//
//                    CustomTextField(
//                        value = email,
//                        onValueChange = { email = it },
//                        label = "Email",
//                        leadingIcon = Icons.Default.Email,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(vertical = 4.dp)
//                    )
//
//                    CustomTextField(
//                        value = password,
//                        onValueChange = { password = it },
//                        label = "Password",
//                        leadingIcon = Icons.Default.Lock,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(vertical = 4.dp)
//                    )
//
//                    TextButton(
//                        onClick = { /* TODO: Forgot Password */ },
//                        modifier = Modifier.align(Alignment.End),
//                        contentPadding = PaddingValues(0.dp)
//                    ) {
//                        Text(
//                            text = "Forgot Password?",
//                            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp),
//                            color = Color.Black,
//                            modifier = Modifier.padding(vertical = 16.dp, horizontal = 20.dp)
//                        )
//                    }
//
//                    Button(
//                        onClick = {
//                            if (email.isNotEmpty() && password.isNotEmpty()) {
//                                viewModel.loginUser(
//                                    UserData(
//                                        email = email,
//                                        password = password
//                                    )
//                                )
//                            } else {
//                                Toast.makeText(
//                                    context,
//                                    "Please fill all the fields",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                            }
//                        },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(horizontal = 60.dp, vertical = 8.dp),
//                        shape = RoundedCornerShape(8.dp),
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = MaterialTheme.colorScheme.primary,
//                            contentColor = Color.White
//                        )
//                    ) {
//                        Text(
//                            text = "Login",
//                            color = Color.White,
//                            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
//                        )
//                    }
//
//                    Row(
//                        modifier = Modifier.padding(top = 8.dp),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Text("Don't have an account?", color = Color.Black)
//                        TextButton(onClick = {
//                            navController.navigate(SubNavigation.LoginSignUpScreen)
//                        }) {
//                            Text(
//                                "Sign Up",
//                                color = MaterialTheme.colorScheme.primary,
//                                fontWeight = FontWeight.Bold
//                            )
//                        }
//                    }
//
//                    Row(
//                        modifier = Modifier.padding(vertical = 16.dp),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        HorizontalDivider(
//                            modifier = Modifier.weight(1f),
//                            thickness = DividerDefaults.Thickness,
//                            color = DividerDefaults.color
//                        )
//                        Text(
//                            text = "Or",
//                            color = Color.Black,
//                            modifier = Modifier.padding(horizontal = 8.dp)
//                        )
//                        HorizontalDivider(
//                            modifier = Modifier.weight(1f),
//                            thickness = DividerDefaults.Thickness,
//                            color = DividerDefaults.color
//                        )
//                    }
//
//                    OutlinedButton(
//                        onClick = {
//                            // TODO: implement Google login
//                        },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(vertical = 8.dp, horizontal = 60.dp),
//                        shape = RoundedCornerShape(8.dp),
//                        enabled = email.isNotBlank() && password.isNotBlank() && !state.value.isLoading
//                    ) {
//                        Image(
//                            painter = painterResource(id = R.drawable.googler),
//                            contentDescription = null,
//                            modifier = Modifier.size(24.dp)
//                        )
//                        Spacer(modifier = Modifier.width(8.dp))
//                        Text(
//                            text = "Login with Google",
//                            color = Color.Black,
//                            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
//                            fontFamily = FontFamily.Cursive
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//





