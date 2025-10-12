package com.example.meeshoclone.presentation.screens


import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.meeshoclone.R // Assuming R file is in this package
import com.example.meeshoclone.domain.models.UserData
import com.example.meeshoclone.domain.models.UserDataParent
import com.example.meeshoclone.presentation.navigation.SubNavigation
import com.example.meeshoclone.presentation.viewModels.ShoppingAppViewModel

import com.google.firebase.auth.FirebaseAuth




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreenUI(
    viewModel: ShoppingAppViewModel = hiltViewModel(),
    firebaseAuth: FirebaseAuth,
    navController: NavController
) {
    LaunchedEffect(key1 = true) {
        viewModel.getUserByID(firebaseAuth.currentUser!!.uid)
    }

    val profileScreenState = viewModel.profileScreenState.collectAsStateWithLifecycle()

    val updateScreenState = viewModel.updateScreenState.collectAsStateWithLifecycle()
    val userProfileImageState = viewModel.uploadUserProfileImageScreenState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }
    val isEditing = remember { mutableStateOf(false) }
    var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }



    // Initialize states safely as empty strings (not nullable)
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val phoneNumber = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    val imageUrl = remember { mutableStateOf("") }

// This effect runs when userData changes
    LaunchedEffect(profileScreenState.value.userData) {
        val userData = profileScreenState.value.userData?.userData
        if (userData != null) {
            firstName.value = userData.firstName ?: ""
            lastName.value = userData.lastName ?: ""
            email.value = userData.email ?: ""
            phoneNumber.value = userData.phoneNumber ?: ""
            address.value = userData.address ?: ""
            imageUrl.value = userData.profileImage ?: ""
        }
    }

    val pickMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.uploadUserProfileImage(uri)
            imageUri = uri
        }
    }

    if (updateScreenState.value.userData != null) {
        Toast.makeText(context, updateScreenState.value.userData, Toast.LENGTH_SHORT).show()
    } else if (updateScreenState.value.error != null) {
        Toast.makeText(context, updateScreenState.value.error, Toast.LENGTH_SHORT).show()
    } else if (updateScreenState.value.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }

    if (userProfileImageState.value.userData != null) {
        imageUrl.value = userProfileImageState.value.userData.toString()
    } else if (userProfileImageState.value.error != null) {
        Toast.makeText(context, userProfileImageState.value.error, Toast.LENGTH_SHORT).show()
    } else if (userProfileImageState.value.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }


    if (profileScreenState.value.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    } else if (profileScreenState.value.error != null) {
        Text(text = profileScreenState.value.error)
    } else if (profileScreenState.value.userData != null) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("ACCOUNT", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                painter = painterResource(id = R.drawable.arrowback),
                                contentDescription = "back button"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                HorizontalDivider(color = Color.LightGray, thickness = 0.5.dp)
                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier.size(120.dp)
                ) {
                    SubcomposeAsyncImage(
                        model = if (isEditing.value && imageUri != null) imageUri else imageUrl.value,
                        contentDescription = "Profile Picture",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .border(2.dp, colorResource(id = R.color.magenta), CircleShape)
                    ) {
                        when (painter.state) {
                            is AsyncImagePainter.State.Loading -> CircularProgressIndicator()
                            is AsyncImagePainter.State.Error -> Icon(Icons.Default.Person, contentDescription = null)
                            else -> SubcomposeAsyncImageContent()
                        }
                    }
                    if (isEditing.value) {
                        IconButton(
                            onClick = { pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                            modifier = Modifier
                                .size(40.dp)
                                .align(Alignment.BottomEnd)
                                .background(MaterialTheme.colorScheme.primary, CircleShape)
                        ) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "Change Picture", tint = Color.White)
                        }
                    }
                }

                Spacer(modifier = Modifier.size(16.dp))

                Row {
                    OutlinedTextField(
                        value = firstName.value,
                        modifier = Modifier.weight(1f),
                        readOnly = if (isEditing.value) false  else true,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = colorResource(id = R.color.magenta),
                            focusedBorderColor = colorResource(id = R.color.magenta)
                        ),
                        shape = RoundedCornerShape(10.dp),
                        onValueChange = { firstName.value = it },
                        label = { Text("First Name") }
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    OutlinedTextField(
                        value = lastName.value,
                        modifier = Modifier.weight(1f),
                        readOnly = if (isEditing.value) false  else true,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = colorResource(id = R.color.magenta),
                            focusedBorderColor = colorResource(id = R.color.magenta)
                        ),
                        onValueChange = { lastName.value = it },
                        shape = RoundedCornerShape(10.dp),
                        label = { Text("Last Name") }
                    )
                }

                Spacer(modifier = Modifier.size(16.dp))

                OutlinedTextField(
                    value = email.value,
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = if (isEditing.value) false  else true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = colorResource(id = R.color.magenta),
                        focusedBorderColor = colorResource(id = R.color.magenta)
                    ),
                    shape = RoundedCornerShape(10.dp),
                    onValueChange = { email.value = it },
                    label = { Text("Email") }
                )

                Spacer(modifier = Modifier.size(16.dp))

                OutlinedTextField(
                    value = phoneNumber.value,
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = if (isEditing.value) false  else true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = colorResource(id = R.color.magenta),
                        focusedBorderColor = colorResource(id = R.color.magenta)
                    ),
                    shape = RoundedCornerShape(10.dp),
                    onValueChange = { phoneNumber.value = it },
                    label = { Text("Phone Number") }
                )

                Spacer(modifier = Modifier.size(16.dp))

                OutlinedTextField(
                    value = address.value,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { address.value = it },
                    readOnly = if (isEditing.value) false  else true,
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = colorResource(id = R.color.magenta),
                        focusedBorderColor = colorResource(id = R.color.magenta)
                    ),
                    label = { Text("Address") }
                )

                Spacer(modifier = Modifier.size(16.dp))

                Button(
                    onClick = { showDialog.value = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.magenta))
                ) {
                    Text("Log Out")
                }

                if (showDialog.value) {
                    LogOutAlertDialog(
                        onDismiss = { showDialog.value = false },
                        onConfirm = {
                            firebaseAuth.signOut()
                            navController.navigate(SubNavigation.LoginSignUpScreen)
                        }
                    )
                }

                Spacer(modifier = Modifier.size(16.dp))

                if (!isEditing.value) {
                    OutlinedButton(
                        onClick = { isEditing.value = !isEditing.value },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Edit Profile")
                    }
                } else {
                    OutlinedButton(
                        onClick = {
                            val updatedUserData = UserData(
                                firstName = firstName.value,
                                lastName = lastName.value,
                                email = email.value,
                                phoneNumber = phoneNumber.value,
                                address = address.value,
                                profileImage = imageUrl.value
                            )
                            val userDataParent = UserDataParent(
                                nodeId = profileScreenState.value.userData!!.nodeId,
                                userData = updatedUserData
                            )
                            viewModel.updateUserData(userDataParent)
                            isEditing.value = !isEditing.value
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Save Profile")
                    }
                }
            }
        }
    }
}

// Placeholder for the custom AlertDialog
@Composable
fun LogOutAlertDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Log Out") },
        text = { Text("Are you sure you want to log out?") },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("Confirm")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}