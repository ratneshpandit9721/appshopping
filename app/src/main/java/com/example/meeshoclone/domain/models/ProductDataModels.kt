package com.example.meeshoclone.domain.models

import kotlinx.serialization.Serializable


@Serializable
class ProductDataModels(


    var productID: String = "",
    var category: String = "",
    var image: String = "",
    var price: String = "",
    var finalPrice: String = "",
    var availableunits: String = "",
    var createdby : String = ""

) {
}