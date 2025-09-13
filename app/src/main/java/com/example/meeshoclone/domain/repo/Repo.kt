package com.example.meeshoclone.domain.repo

import android.net.Uri
import com.example.meeshoclone.common.ResultState
import com.example.meeshoclone.domain.models.BannerDataModels
import com.example.meeshoclone.domain.models.CartDataModels
import com.example.meeshoclone.domain.models.CategoryDataModels
import com.example.meeshoclone.domain.models.ProductDataModels
import com.example.meeshoclone.domain.models.UserData
import com.example.meeshoclone.domain.models.UserDataParent
import kotlinx.coroutines.flow.Flow

interface Repo {

    fun registerUserWithEmailAndPassword(userData: UserData) : Flow<ResultState<String>>
    fun loginUserWithEmailAndPassword (userData: UserData) : Flow<ResultState<String>>
    fun getUserByID (uid : String) : Flow<ResultState<UserDataParent>>
    fun updateUserData (userDataParent: UserDataParent) : Flow<ResultState<String>>
    fun userProfileImage (uri: Uri) : Flow<ResultState<String>>
    fun getCategoriesInLimited () : Flow<ResultState<List<CategoryDataModels>>>
    fun getProductsInLimited () : Flow<ResultState<List<ProductDataModels>>>
    fun getAllProducts() : Flow<ResultState<List<ProductDataModels>>>
    fun getProductById (productId : String) : Flow<ResultState<ProductDataModels>>
    fun addToCart (cartDataModels: CartDataModels) : Flow<ResultState<String>>
    fun addToFav (productDataModels: ProductDataModels): Flow<ResultState<String>>
    fun getAllFav (): Flow<ResultState<List<ProductDataModels>>>
    fun getCart (): Flow<ResultState<List<CartDataModels>>>
    fun getAllCategories (): Flow<ResultState<List<CategoryDataModels>>>
    fun getCheckout (productId: String): Flow<ResultState<ProductDataModels>>
    fun getBanner (): Flow<ResultState<List<BannerDataModels>>>
    fun getSpecificCategoryItems (categoryName : String): Flow<ResultState<List<ProductDataModels>>>
    fun getAllSuggestedProduct (): Flow<ResultState<List<ProductDataModels>>>
}