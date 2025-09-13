package com.example.meeshoclone.presentation.viewModels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meeshoclone.common.HomeScreenState
import com.example.meeshoclone.common.ResultState
import com.example.meeshoclone.domain.models.ProductDataModels
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
    val userData: List<ProductDataModels>? = emptyList(),
    val error: String = ""
)

data class GetAllCategoryState(
    val isLoading: Boolean = false,
    val userData: List<ProductDataModels>? = emptyList(),
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


