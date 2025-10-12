package com.example.meeshoclone.presentation.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.meeshoclone.R
import com.example.meeshoclone.presentation.navigation.Routes




// This is the New TopBar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable

fun TopBarComponent(scrollBehavior: TopAppBarScrollBehavior, navController: NavController){
    TopAppBar(
        modifier = Modifier.fillMaxWidth().background(Color.White),
        title = {
            Column(verticalArrangement = Arrangement.Center){
                Text (text = "Hello",
                    color = Color.Black,
                    fontSize = 14.sp,
                    lineHeight = 16.sp)

                Text (text = "Ratnesh",
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 16.sp)

            }
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.navigate(Routes.ProfileScreen)
                }
            ){
                Icon(
                    painter = painterResource(R.drawable.profile),
                    contentDescription = "Navigate to profile Screen",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(34.dp)
                )
            }
        }
    )
}
