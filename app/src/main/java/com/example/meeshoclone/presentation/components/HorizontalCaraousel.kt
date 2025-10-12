package com.example.meeshoclone.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.meeshoclone.R


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
        CaraouselItem(0,R.drawable.carousel__, "Photo 1"),
        CaraouselItem(0,R.drawable.carousel_1, "Photo 2"),
        CaraouselItem(0,R.drawable.caraousel_2, "Photo 3"),
        CaraouselItem(0,R.drawable.carousel_3, "Photo 4"),
        CaraouselItem(0,R.drawable.caraousel_5, "Photo 5"),

    )

    val state = rememberCarouselState { items.count() }

    HorizontalMultiBrowseCarousel(
        state = state,
        modifier = Modifier.width(412.dp).height(221.dp),
        preferredItemWidth = 186.dp,
        itemSpacing = 8.dp,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {i->
    val item = items[1]
    Image(
        modifier = Modifier.height(205.dp).clip(MaterialTheme.shapes.extraLarge),
        painter = painterResource(id = item.imageResId),
        contentDescription = item.contentDescDescription,
        contentScale = ContentScale.Crop
    )


    }
}



