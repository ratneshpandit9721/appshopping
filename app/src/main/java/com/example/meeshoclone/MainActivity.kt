//package com.example.meeshoclone
//
//import android.app.Activity
//import android.os.Bundle
//import android.os.Handler
//import android.os.Looper
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.text.BasicText
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//
//import com.example.meeshoclone.presentation.navigation.App
//import com.example.meeshoclone.ui.theme.MeeshoCloneTheme
//import com.google.firebase.auth.FirebaseAuth
//import com.razorpay.Checkout
//import okhttp3.internal.http2.Http2Reader
//
//
//import android.widget.Toast
//import com.razorpay.PaymentData
//
//
//import com.razorpay.PaymentResultListener
//import com.razorpay.PaymentResultWithDataListener
//import dagger.hilt.android.AndroidEntryPoint
//import org.json.JSONObject
//
//@AndroidEntryPoint
//class MainActivity : ComponentActivity() , PaymentResultWithDataListener {
//    private lateinit var firebaseAuth: FirebaseAuth
//    override fun onCreate(savedInstanceState: Bundle?) {
//
//        super.onCreate(savedInstanceState)
//        Checkout.preload(applicationContext)
//        firebaseAuth = FirebaseAuth.getInstance()
//        enableEdgeToEdge()
//        setContent {
//            MeeshoCloneTheme {
//
//                MainScreen(firebaseAuth = firebaseAuth) { }
//
//            }
//        }
//    }
//
//
//    @Composable
//    fun MainScreen(firebaseAuth: FirebaseAuth, payTest: () -> Unit) {
//
//        val showSplash = remember {
//            mutableStateOf(true)
//        }
//
//        LaunchedEffect(Unit) {
//            Handler(Looper.getMainLooper()).postDelayed({
//                showSplash.value = false
//            }, 3000)
//        }
//        if (showSplash.value) {
//            SplashScreen()
//        } else {
//
//            App(firebaseAuth = firebaseAuth, { payTest() })
//
//        }
//    }
//
//    @Composable
//    fun SplashScreen() {
//        Box(
//            modifier = Modifier.fillMaxSize()
//                .background(color = Color.Black),
//            contentAlignment = Alignment.Center
//
//        ) {
//
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.caraousel_5),
//                    contentDescription = "App Icon",
//                    modifier = Modifier.size(300.dp)
//                )
//
//                BasicText(
//                    text = "Welcome to Store ",
//                    style = MaterialTheme.typography.labelLarge.copy(
//                        color = Color.White,
//                        fontSize = 18.sp,
//                        textAlign = TextAlign.Center
//                    )
//
//                )
//            }
//        }
//    }
//
//
//    fun payTest() {
//        val checkout = Checkout()
//        checkout.setKeyID("YOUR_RAZORPAY_KEY") // Replace with your key
//
//        try {
//            val options = JSONObject()
//            options.put("name", "My App")
//            options.put("description", "Test Payment")
//            options.put("currency", "INR")
//            options.put("amount", "50000") // Amount in paise (50000 = ₹500)
//
//            val prefill = JSONObject()
//            prefill.put("email", "test@example.com")
//            prefill.put("contact", "9876543210")
//
//            options.put("prefill", prefill)
//
//            checkout.open(this@MainActivity, options)
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//            Toast.makeText(this, "Error in payment: ${e.message}", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    override fun onPaymentSuccess(razorpayPaymentID: String, p1: PaymentData) {
//        Toast.makeText(this, "Payment Successful: $razorpayPaymentID", Toast.LENGTH_SHORT).show()
//    }
//
//    override fun onPaymentError(code: Int, response: String, p2: PaymentData) {
//        Toast.makeText(this, "Payment failed: $response", Toast.LENGTH_SHORT).show()
//    }
//}
//


package com.example.meeshoclone

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.meeshoclone.presentation.navigation.App
import com.example.meeshoclone.presentation.navigation.Routes
import com.example.meeshoclone.ui.theme.MeeshoCloneTheme
import com.google.firebase.auth.FirebaseAuth
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), PaymentResultWithDataListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Checkout.preload(applicationContext)
        val firebaseAuth = FirebaseAuth.getInstance()
        enableEdgeToEdge()

        setContent {
            MeeshoCloneTheme {
                MainScreen(firebaseAuth)
            }
        }
    }

    @Composable
    fun MainScreen(firebaseAuth: FirebaseAuth) {
        var showSplash by remember { mutableStateOf(true) }

        LaunchedEffect(Unit) {
            Handler(Looper.getMainLooper()).postDelayed({ showSplash = false }, 3000)
        }

        if (showSplash) {
            SplashScreen()
        } else {
            App(firebaseAuth) { payTest() }
        }
    }

    @Composable
    fun SplashScreen() {
        Box(
            modifier = Modifier.fillMaxSize().background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Image(painter = painterResource(id = R.drawable.caraousel_5), contentDescription = null, modifier = Modifier.size(300.dp))
                BasicText("Welcome to Store", style = MaterialTheme.typography.labelLarge.copy(fontSize = 18.sp, color = Color.White))
            }
        }
    }

    fun payTest() {
        val checkout = Checkout()
        checkout.setKeyID("YOUR_RAZORPAY_KEY")
        Toast.makeText(this, "Payment test invoked", Toast.LENGTH_SHORT).show()
    }

    override fun onPaymentSuccess(razorpayPaymentID: String, p1: PaymentData) {}
    override fun onPaymentError(code: Int, response: String, p2: PaymentData) {}
}
