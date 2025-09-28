package com.example.meeshoclone.presentation.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable


//Horizontal carousel made for Home Screen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

@Composable

fun HorizontalCarousel(){
    data class CaraouselItem(
        val id: Int,
        @DrawableRes val imageResId: Int,
        val contentDescDescription: String
    )

    val  items = listOf(
        CaraouselItem(0,R.drawable.)
    )
}



