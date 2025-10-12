package com.example.meeshoclone.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.meeshoclone.R

// This is the Low price card used in Home Screen which have yellow gradient as background

@Composable

fun LowPriceCard(cardData: LowPriceCardItem)
{
    Column(
        modifier =Modifier.clip(shape = RoundedCornerShape(4.dp)).background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        Box(
            modifier= Modifier.size(width = 115.dp, height = 125.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFACF2E),
                            Color(0xFF8C0748)
                        )
                    ),
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(start = 4.dp, top = 4.dp, end = 6.dp, bottom = 6.dp) // This padding act as border thickness
        ){

            Box(modifier = Modifier
                .fillMaxSize().shadow(elevation = 2.dp, shape = RoundedCornerShape(4.dp))
                .size(width = 110.dp, height = 120.dp)
                .clip(shape = RoundedCornerShape(4.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFF8EDC9),  // light color at top
                            Color(0xFFD2AC40)    //slightly deeper color at bottom
                        )
                    )

            ),
                contentAlignment = Alignment.BottomCenter,
                ){

                Image(
                    painter = painterResource(R.drawable.caraousel_2),
                    contentDescription = "boy image"
                )
                Card(modifier = Modifier
                    .padding(bottom = 8.dp)
                    .size(width = 45.dp,height = 28.dp)
                    .align(Alignment.BottomEnd),
                    shape = RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp),
                    ){
                    Column(modifier = Modifier.fillMaxSize()
                        .background(brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFFFACF2E),
                                Color(0xFFD2A706)
                            )
                        ))
                        .padding(2.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center){

                        Text (text = "Under",
                            fontSize = 10.sp,
                            lineHeight = 6.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF8C0748))
                        Text (text = cardData.price,
                            fontSize = 10.sp,
                            lineHeight = 6.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF8C0748)
                            )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = cardData.cardName, color = Color.Black)
    }
}

data class LowPriceCardItem(
    val image :Int,
    val price : String,
    val cardName : String
)

@Preview(showBackground = true)
@Composable
fun PreviewCard(){
    LowPriceCard(
        cardData = LowPriceCardItem(
            image = 1,
            price = "$299",
            cardName = "Kids Boys"
        )
    )
}
