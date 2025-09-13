package com.example.meeshoclone.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.meeshoclone.R

@Preview(showSystemUi = true)
@Composable

fun LoginScreenUI() {

    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }



    Column(

        modifier = Modifier
            .fillMaxSize().background(MaterialTheme.colorScheme.background)
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
            onValueChange = { it },
            label = "Email",
            leadingIcon = Icons.Default.Email,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)

        )
        CustomTextField(
            value = password,
            onValueChange = { it },
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
                if (email.isNotEmpty() && password.isNotEmpty()

                ) {
                    if (email == "admin" && password == "admin") {


                        Toast.makeText(context, "Login Successfully", Toast.LENGTH_SHORT).show()
                    } else {


                        Toast.makeText(context, "Invalid email or password", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {


                    Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 60.dp, vertical = 1.dp),
            shape = RoundedCornerShape(8.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
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
                onClick = { /*TODO*/ },
                contentPadding = PaddingValues(0.dp)
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
            Text(text = "Or", color = Color.Black, modifier = Modifier.padding(horizontal = 8.dp))
            HorizontalDivider(modifier = Modifier.weight(1f))


        }

        OutlinedButton(
            onClick = {},
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