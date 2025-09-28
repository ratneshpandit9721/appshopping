package com.example.meeshoclone.presentation.viewModels


import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meeshoclone.common.HomeScreenState
import com.example.meeshoclone.common.ResultState
import com.example.meeshoclone.domain.models.CartDataModels
import com.example.meeshoclone.domain.models.CategoryDataModels
import com.example.meeshoclone.domain.models.ProductDataModels
import com.example.meeshoclone.domain.models.UserData
import com.example.meeshoclone.domain.models.UserDataParent
import com.example.meeshoclone.domain.usecase.AddtoCartUsecase
import com.example.meeshoclone.domain.usecase.AddtoFavUsecase
import com.example.meeshoclone.domain.usecase.GetBannerUsecase
import com.example.meeshoclone.domain.usecase.CreateUserUsecase
import com.example.meeshoclone.domain.usecase.GetAllCategoriesUsecase
import com.example.meeshoclone.domain.usecase.GetAllFavUsecase
import com.example.meeshoclone.domain.usecase.GetAllProductById
import com.example.meeshoclone.domain.usecase.GetAllProductUsecase
import com.example.meeshoclone.domain.usecase.GetAllSuggestedProductsUsecase
import com.example.meeshoclone.domain.usecase.GetCartUsecase
import com.example.meeshoclone.domain.usecase.GetCategoryInLimitUsecase
import com.example.meeshoclone.domain.usecase.GetCheckoutUsecase
import com.example.meeshoclone.domain.usecase.GetProductInLimitUsecase
import com.example.meeshoclone.domain.usecase.GetSpecificCategoryUsecase
import com.example.meeshoclone.domain.usecase.GetUserUsecase
import com.example.meeshoclone.domain.usecase.LoginUserUsecases
import com.example.meeshoclone.domain.usecase.UpdateDataUsecase
import com.example.meeshoclone.domain.usecase.UserProfileImageUsecase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject


class ShoppingAppViewModel @Inject constructor(


    private val createUserUsecase: CreateUserUsecase,
    private val loginUsecase: LoginUserUsecases,
    private val getUserUsecase: GetUserUsecase,
    private val updateDataUsecase: UpdateDataUsecase,
    private val getAllProductUsecase: GetAllProductUsecase,
    private val getProductByIDUsecase: GetAllProductById,
    private val getCheckoutUsecase: GetCheckoutUsecase,
    private val getAllSuggestedProductUsecase: GetAllSuggestedProductsUsecase,
    private val getSpecificCategoryUsecase: GetSpecificCategoryUsecase,
    private val getCartUsecase: GetCartUsecase,
    private val getAllFavUsecase: GetAllFavUsecase,
    private val getAllCategoryUsecase: GetAllCategoriesUsecase,
    private val getProductInLimitUsecase: GetProductInLimitUsecase,
    private val getBannerUsecase: GetBannerUsecase,
    private val getCategoryInLimitUsecase: GetCategoryInLimitUsecase,
    private val addtoCartUsecase: AddtoCartUsecase,
    private val addToFavUsecase: AddtoFavUsecase,
    private val userProfileImageUsecase: UserProfileImageUsecase,


    ) : ViewModel() {

    private val _signUpScreenState = MutableStateFlow(SignupScreenState())
    val signupScreenState = _signUpScreenState.asStateFlow()

    private val _loginScreenState = MutableStateFlow(LoginScreenState())
    val loginScreenState = _loginScreenState.asStateFlow()

    private val _profileScreenState = MutableStateFlow(ProfileScreenState())
    val profileScreenState = _profileScreenState.asStateFlow()

    private val _updateScreenState = MutableStateFlow(UpdateScreenState())
    val updateScreenState = _updateScreenState.asStateFlow()

    private val _uploadUserProfileImageScreenState =
        MutableStateFlow(UploadUserProfileImageScreenState())
    val uploadUserProfileImageScreenState = _uploadUserProfileImageScreenState.asStateFlow()

    private val _addToCartState = MutableStateFlow(AddToCartState())
    val addToCartState = _addToCartState.asStateFlow()

    private val _getProductByIDState = MutableStateFlow(GetProductByIDState())
    val getProductByIDState = _getProductByIDState.asStateFlow()

    private val _addToFavState = MutableStateFlow(AddToFavState())
    val addToFavState = _addToFavState.asStateFlow()

    private val _getAllFavState = MutableStateFlow(GetAllFavState())
    val getAllFavState = _getAllFavState.asStateFlow()

    private val _getAllProductState = MutableStateFlow(GetAllProductState())
    val getAllProductState = _getAllProductState.asStateFlow()

    private val _getCartState = MutableStateFlow(GetCartState())
    val getCartState = _getCartState.asStateFlow()

    private val _getAllCategoryState = MutableStateFlow(GetAllCategoryState())
    val getAllCategoryState = _getAllCategoryState.asStateFlow()

    private val _getCheckoutState = MutableStateFlow(GetCheckoutState())
    val getCheckoutState = _getCheckoutState.asStateFlow()

    private val _getSpecificCategoryItemsState = MutableStateFlow(GetSpecificCategoryItemsState())
    val getSpecificCategoryItemsState = _getSpecificCategoryItemsState.asStateFlow()

    private val _getAllSuggestedProductState = MutableStateFlow(GetAllSuggestedProductState())
    val getAllSuggestedProductState = _getAllSuggestedProductState.asStateFlow()

    private val _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState = _homeScreenState.asStateFlow()


    fun getSpecificCategoryItems(categoryName: String) {

        viewModelScope.launch {
            getSpecificCategoryUsecase.getSpecificCategoryItems(categoryName).collect {
                when (it) {
                    is ResultState.Error -> {
                        _getSpecificCategoryItemsState.value =
                            _getSpecificCategoryItemsState.value.copy(
                                isLoading = false,
                                error = it.message
                            )

                    }

                    is ResultState.Loading -> {
                        _getSpecificCategoryItemsState.value =
                            _getSpecificCategoryItemsState.value.copy(
                                isLoading = true
                            )
                    }

                    is ResultState.Success -> {

                        _getSpecificCategoryItemsState.value =
                            _getSpecificCategoryItemsState.value.copy(
                                isLoading = false,
                                userData = it.data
                            )
                    }

                }
            }

        }
    }

    fun getCheckOut(productId: String) {
        viewModelScope.launch {
            getCheckoutUsecase.getCheckout(productId).collect {
                when (it) {
                    is ResultState.Error -> {
                        _getCheckoutState.value = _getCheckoutState.value.copy(
                            isLoading = false,
                            error = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _getCheckoutState.value = _getCheckoutState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _getCheckoutState.value = _getCheckoutState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }

        }

    }

    fun getAllCategories() {
        viewModelScope.launch {

            getAllCategoryUsecase.getAllCategories().collect {
                when (it) {
                    is ResultState.Error -> {

                        _getAllCategoryState.value = _getAllCategoryState.value.copy(
                            isLoading = false,
                            error = it.message
                        )

                    }

                    is ResultState.Loading -> {
                        _getAllCategoryState.value = _getAllCategoryState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _getAllCategoryState.value = _getAllCategoryState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }

            }

        }

    }


    fun getCart() {
        viewModelScope.launch {
            getCartUsecase.getCart().collect {
                when (it) {
                    is ResultState.Error -> {
                        _getCartState.value = _getCartState.value.copy(
                            isLoading = false,
                            error = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _getCartState.value = _getCartState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _getCartState.value = _getCartState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }
        }
    }

    fun getAllProducts() {
        viewModelScope.launch {
            getAllProductUsecase.getAllProduct().collect {
                when (it) {
                    is ResultState.Error -> {
                        _getAllProductState.value = _getAllProductState.value.copy(
                            isLoading = false,
                            error = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _getAllProductState.value = _getAllProductState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _getAllProductState.value = _getAllProductState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }

                }

            }

        }
    }


    fun getAllFav() {
        viewModelScope.launch {
            getAllFavUsecase.getAllFav().collect {
                when (it) {
                    is ResultState.Error -> {
                        _getAllFavState.value = _getAllFavState.value.copy(
                            isLoading = false,
                            error = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _getAllFavState.value = _getAllFavState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _getAllFavState.value = _getAllFavState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }

                }
            }
        }
    }

    fun addToFav(productDataModels: ProductDataModels) {
        viewModelScope.launch {
            addToFavUsecase.addToFav(productDataModels).collect {
                when (it) {

                    is ResultState.Error -> {
                        _addToFavState.value = _addToFavState.value.copy(
                            isLoading = false,
                            error = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _addToFavState.value = _addToFavState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _addToFavState.value = _addToFavState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )

                    }
                }
            }
        }
    }

    fun getProductByID(productId: String) {
        viewModelScope.launch {
            getProductByIDUsecase.getProductById(productId).collect {
                when (it) {
                    is ResultState.Error -> {
                        _getProductByIDState.value = _getProductByIDState.value.copy(
                            isLoading = false,
                            error = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _getProductByIDState.value = _getProductByIDState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _getProductByIDState.value = _getProductByIDState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }

                }
            }
        }

    }

    fun addToCart(cartDataModels: CartDataModels) {
        viewModelScope.launch {
            addtoCartUsecase.addToCard(cartDataModels).collect {
                when (it) {
                    is ResultState.Error -> {
                        _addToCartState.value = _addToCartState.value.copy(
                            isLoading = false,
                            error = it.message
                        )

                    }

                    is ResultState.Loading -> {
                        _addToCartState.value = _addToCartState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _addToCartState.value = _addToCartState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }
        }
    }


    init {
        loadHomeScreenData()
    }

    fun loadHomeScreenData() {
        viewModelScope.launch {
            combine(
                getCategoryInLimitUsecase.getCategoriesInLimited(),
                getProductInLimitUsecase.getProductsInLimited(),
                getBannerUsecase.getBanner()


            ) { categoriesResult, productResult, bannerResult ->

                when {
                    categoriesResult is ResultState.Error -> {
                        HomeScreenState(isLoading = false, errorMessage = categoriesResult.message)

                    }

                    productResult is ResultState.Error -> {
                        HomeScreenState(isLoading = false, errorMessage = productResult.message)
                    }

                    bannerResult is ResultState.Error -> {
                        HomeScreenState(isLoading = false, errorMessage = bannerResult.message)
                    }

                    categoriesResult is ResultState.Success && productResult is ResultState.Success && bannerResult is ResultState.Success -> {
                        HomeScreenState(
                            isLoading = false,
                            categories = categoriesResult.data,
                            products = productResult.data,
                            banner = bannerResult.data
                        )

                    }

                    else -> HomeScreenState(isLoading = true)
                }

            }.collect { state ->
                _homeScreenState.value = state
            }
        }
    }

    fun uploadUserProfileImage(uri: Uri) {
        viewModelScope.launch {
            userProfileImageUsecase.userProfileImage(uri).collect {
                when (it) {
                    is ResultState.Error -> {
                        _uploadUserProfileImageScreenState.value =
                            _uploadUserProfileImageScreenState.value.copy(
                                isLoading = false,
                                error = it.message
                            )
                    }

                    is ResultState.Loading -> {
                        _uploadUserProfileImageScreenState.value =
                            _uploadUserProfileImageScreenState.value.copy(
                                isLoading = true
                            )
                    }

                    is ResultState.Success -> {
                        _uploadUserProfileImageScreenState.value =
                            _uploadUserProfileImageScreenState.value.copy(
                                isLoading = false,
                                userData = it.data
                            )
                    }
                }

            }
        }
    }

    fun updateUserData(userDataParent: UserDataParent) {
        viewModelScope.launch {
            updateDataUsecase.updateUserData(userDataParent = userDataParent).collect {
                when (it) {
                    is ResultState.Error -> {
                        _updateScreenState.value = _updateScreenState.value.copy(
                            isLoading = false,
                            error = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _updateScreenState.value = _updateScreenState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _updateScreenState.value = _updateScreenState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }
        }
    }

    fun createUserData(userData: UserData) {
        viewModelScope.launch {
            createUserUsecase.createUser(userData).collect {
                when (it) {
                    is ResultState.Error -> {
                        _signUpScreenState.value = _signUpScreenState.value.copy(
                            isLoading = false,
                            error = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _signUpScreenState.value = _signUpScreenState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _signUpScreenState.value = _signUpScreenState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }
        }
    }

    fun loginUser(userData: UserData) {
        viewModelScope.launch {
            loginUsecase.loginUserWithEmailAndPassword(userData).collect {
                when (it) {
                    is ResultState.Error -> {
                        _loginScreenState.value = _loginScreenState.value.copy(
                            isLoading = false,
                            error = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _loginScreenState.value = _loginScreenState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _loginScreenState.value = _loginScreenState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }
        }
    }

    fun getUserByID(uid: String) {
        viewModelScope.launch {
            getUserUsecase.getUserByID(uid).collect {
                when (it) {
                    is ResultState.Error -> {
                        _profileScreenState.value = _profileScreenState.value.copy(
                            isLoading = false,
                            error = it.message
                        )
                    }

                    is ResultState.Loading -> {
                        _profileScreenState.value = _profileScreenState.value.copy(
                            isLoading = true
                        )
                    }

                    is ResultState.Success -> {
                        _profileScreenState.value = _profileScreenState.value.copy(
                            isLoading = false,
                            userData = it.data
                        )
                    }
                }
            }

        }
    }

    fun getAllSuggestedProducts() {
        viewModelScope.launch {
            getAllSuggestedProductUsecase.getAllSuggestedProduct().collect {
                when (it) {
                    is ResultState.Error -> {
                        _getAllSuggestedProductState.value =
                            _getAllSuggestedProductState.value.copy(
                                isLoading = false,
                                error = it.message
                            )
                    }

                    is ResultState.Loading -> {
                        _getAllSuggestedProductState.value =
                            _getAllSuggestedProductState.value.copy(
                                isLoading = true
                            )
                    }

                    is ResultState.Success -> {
                        _getAllSuggestedProductState.value =
                            _getAllSuggestedProductState.value.copy(
                                isLoading = false,
                                userData = it.data
                            )
                    }
                }
            }

        }
    }
}

    data class ProfileScreenState(
        val isLoading: Boolean = false,
        val userData: UserDataParent? = null,
        val error: String = ""


    )

    data class SignupScreenState(
        val isLoading: Boolean = false,
        val userData: String? = null,
        val error: String = ""


    )

    data class LoginScreenState(
        val isLoading: Boolean = false,
        val userData: String? = null,
        val error: String = ""


    )

    data class UpdateScreenState(
        val isLoading: Boolean = false,
        val userData: String? = null,
        val error: String = ""


    )

    data class UploadUserProfileImageScreenState(
        val isLoading: Boolean = false,
        val userData: String? = null,
        val error: String = ""


    )

    data class AddToCartState(
        val isLoading: Boolean = false,
        val userData: String? = null,
        val error: String = ""


    )

    data class GetProductByIDState(
        val isLoading: Boolean = false,
        val userData: ProductDataModels? = null,
        val error: String = ""

    )

    data class AddToFavState(
        val isLoading: Boolean = false,
        val userData: String? = null,
        val error: String = ""

    )

    data class GetAllFavState(
        val isLoading: Boolean = false,
        val userData: List<ProductDataModels>? = emptyList(),
        val error: String = ""
    )

    data class GetAllProductState(
        val isLoading: Boolean = false,
        val userData: List<ProductDataModels>? = emptyList(),
        val error: String = ""
    )

    data class GetCartState(
        val isLoading: Boolean = false,
        val userData: List<CartDataModels>? = emptyList(),
        val error: String = ""
    )

    data class GetAllCategoryState(
        val isLoading: Boolean = false,
        val userData: List<CategoryDataModels>? = emptyList(),
        val error: String = ""
    )


    data class GetCheckoutState(
        val isLoading: Boolean = false,
        val userData: ProductDataModels? = null,
        val error: String = ""
    )

    data class GetSpecificCategoryItemsState(
        val isLoading: Boolean = false,
        val userData: List<ProductDataModels>? = emptyList(),
        val error: String = ""
    )

    data class GetAllSuggestedProductState(
        val isLoading: Boolean = false,
        val userData: List<ProductDataModels>? = emptyList(),
        val error: String = ""
    )






