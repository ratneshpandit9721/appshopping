package com.example.meeshoclone.common

import com.example.meeshoclone.domain.models.BannerDataModels
import com.example.meeshoclone.domain.models.CategoryDataModels
import com.example.meeshoclone.domain.models.ProductDataModels

data class HomeScreenState (

    val isLoading : Boolean = true,
    val errorMessage: String? = null,

    val categories : List<CategoryDataModels>? = null,
    val products : List<ProductDataModels>? = null,
    val banner : List<BannerDataModels>? = null,



    )
