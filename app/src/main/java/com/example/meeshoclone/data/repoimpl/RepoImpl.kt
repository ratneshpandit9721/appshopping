package com.example.meeshoclone.data.repoimpl

import android.R.attr.data
import android.net.Uri
import com.example.meeshoclone.common.ADD_TO_CART
import com.example.meeshoclone.common.ADD_TO_FAV
import com.example.meeshoclone.common.PRODUCT_COLLECTION
import com.example.meeshoclone.common.ResultState
import com.example.meeshoclone.common.USER_COLLECTION
import com.example.meeshoclone.domain.models.BannerDataModels
import com.example.meeshoclone.domain.models.CartDataModels
import com.example.meeshoclone.domain.models.CategoryDataModels
import com.example.meeshoclone.domain.models.ProductDataModels
import com.example.meeshoclone.domain.models.UserData
import com.example.meeshoclone.domain.models.UserDataParent
import com.example.meeshoclone.domain.repo.Repo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import jakarta.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


class RepoImpl @Inject constructor
    (
    var firebaseAuth: FirebaseAuth, var firebaseFirestore: FirebaseFirestore
) : Repo {
    override fun registerUserWithEmailAndPassword(userData: UserData): Flow<ResultState<String>> =
        callbackFlow {

            trySend(ResultState.Loading)
            firebaseAuth.createUserWithEmailAndPassword(userData.email, userData.password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        firebaseFirestore.collection(USER_COLLECTION)
                            .document(it.result.user?.uid.toString()).set(userData)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    trySend(ResultState.Success("User Registered Successfully"))
                                } else {
                                    if (it.exception != null) {
                                        trySend(ResultState.Error(it.exception?.localizedMessage.toString()))

                                    }
                                }

                            }
                        trySend(ResultState.Success("User Registered Successfully"))
                    } else {
                        if (it.exception != null) {
                            trySend(ResultState.Error(it.exception?.localizedMessage.toString()))

                        }
                    }


                }

            awaitClose {
                close()
            }
        }


    override fun loginUserWithEmailAndPassword(userData: UserData): Flow<ResultState<String>> =
        callbackFlow {

            trySend(ResultState.Loading)
            firebaseAuth.signInWithEmailAndPassword(userData.email, userData.password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        trySend(ResultState.Success("User Logged In Successfully"))
                    } else {
                        if (it.exception != null) {
                            trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                        }
                    }


                }

            awaitClose { close() }
        }


    override fun getUserByID(uid: String): Flow<ResultState<UserDataParent>> = callbackFlow {

        trySend(ResultState.Loading)

        firebaseFirestore.collection(USER_COLLECTION).document(uid).get().addOnCompleteListener {

            if (it.isSuccessful) {
                val userData = it.result.toObject(UserData::class.java)!!
                val userDataParent = UserDataParent(it.result.id, userData)
                trySend(ResultState.Success(userDataParent))
            } else {
                if (it.exception != null) {
                    trySend(ResultState.Error(it.exception?.localizedMessage.toString()))

                }
            }


        }
        awaitClose { close() }
    }


    override fun updateUserData(userDataParent: UserDataParent): Flow<ResultState<String>> =
        callbackFlow {

            trySend(ResultState.Loading)

            firebaseFirestore.collection(USER_COLLECTION).document(userDataParent.nodeId)
                .update(userDataParent.userData.toMap()).addOnCompleteListener {


                    if (it.isSuccessful) {
                        trySend(ResultState.Success("User Data Updated Successfully"))
                    } else {
                        if (it.exception != null) {
                            trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                        }


                    }

                }

            awaitClose { close() }


        }


    override fun userProfileImage(uri: Uri): Flow<ResultState<String>> = callbackFlow {

        trySend(ResultState.Loading)
        FirebaseStorage.getInstance().reference.child("userProfileImage\${System.currentTimeMillis()}+ ${firebaseAuth.currentUser?.uid}")
            .putFile(uri).addOnCompleteListener {

                it.result.storage.downloadUrl.addOnSuccessListener { imageUri ->
                    trySend(ResultState.Success(imageUri.toString()))
                }
                if (it.exception != null) {

                    trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                }

            }

        awaitClose { close() }
    }


    override fun getCategoriesInLimited(): Flow<ResultState<List<CategoryDataModels>>> =
        callbackFlow {

            trySend(ResultState.Loading)

            firebaseFirestore.collection("Category").limit(5).get()
                .addOnSuccessListener { querySnapshot ->

                    val Category = querySnapshot.documents.mapNotNull { document ->
                        document.toObject(CategoryDataModels::class.java)

                    }
                    trySend(ResultState.Success(Category))
                }.addOnFailureListener { exception ->

                    trySend(ResultState.Error(exception.toString()))

                }

            awaitClose { close() }
        }


    override fun getProductsInLimited(): Flow<ResultState<List<ProductDataModels>>> = callbackFlow {

        trySend(ResultState.Loading)

        firebaseFirestore.collection("Products").limit(15).get().addOnSuccessListener {

            val Products = it.documents.mapNotNull { document ->
                document.toObject(ProductDataModels::class.java)?.apply {
                    productID = document.id
                }
            }
            trySend(ResultState.Success(Products))
        }.addOnFailureListener {

            trySend(ResultState.Error(it.toString()))
        }

        awaitClose { close() }


    }

    override fun getAllProducts(): Flow<ResultState<List<ProductDataModels>>> = callbackFlow {
        trySend(ResultState.Loading)


        firebaseFirestore.collection("Products").get().addOnSuccessListener {
            val Products = it.documents.mapNotNull { document ->
                document.toObject(ProductDataModels::class.java)?.apply {
                    productID = document.id
                }
            }
            trySend(ResultState.Success(Products))

        }.addOnFailureListener {
            trySend(ResultState.Error(it.toString()))

        }
        awaitClose { close() }

    }


    override fun getProductById(productId: String): Flow<ResultState<ProductDataModels>> =
        callbackFlow {
            trySend(ResultState.Loading)
            firebaseFirestore.collection(PRODUCT_COLLECTION).document(productId).get()
                .addOnSuccessListener {

                    val product = it.toObject(ProductDataModels::class.java)
                    trySend(ResultState.Success(product!!))
                }.addOnFailureListener {
                    trySend(ResultState.Error(it.toString()))

                }
            awaitClose { close() }

        }


    override fun addToCart(cartDataModels: CartDataModels): Flow<ResultState<String>> =
        callbackFlow {

            trySend(ResultState.Loading)
            firebaseFirestore.collection(ADD_TO_CART).document(firebaseAuth.currentUser!!.uid)
                .collection("User_Cart")
                .add(cartDataModels).addOnSuccessListener {
                    trySend(ResultState.Success("Added to Cart"))
                }.addOnFailureListener {
                    trySend(ResultState.Error(it.toString()))
                }
            awaitClose { close() }

        }


    override fun addToFav(productDataModels: ProductDataModels): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)

            firebaseFirestore.collection(ADD_TO_FAV).document(firebaseAuth.currentUser!!.uid)
                .collection("User_Fav")
                .add(productDataModels).addOnSuccessListener {

                    trySend(ResultState.Success("Added to Fav"))
                }.addOnFailureListener {
                    trySend(ResultState.Error(it.toString()))

                }
            awaitClose { close() }

        }

    override fun getAllFav(): Flow<ResultState<List<ProductDataModels>>> = callbackFlow {

        trySend(ResultState.Loading)
        firebaseFirestore.collection(ADD_TO_FAV).document(firebaseAuth.currentUser!!.uid)
            .collection("User_Fav")
            .get().addOnSuccessListener {
                val fav = it.documents.mapNotNull { document ->
                    document.toObject(ProductDataModels::class.java)
                        ?.apply { productID = document.id }
                }
                trySend(ResultState.Success(fav))
            }.addOnFailureListener {
                trySend(ResultState.Error(it.toString()))
            }

        awaitClose { close() }
    }


    override fun getCart(): Flow<ResultState<List<CartDataModels>>> = callbackFlow {
        trySend(ResultState.Loading)

        firebaseFirestore.collection(ADD_TO_CART).document(firebaseAuth.currentUser!!.uid)
            .collection("User_Cart")
            .get().addOnSuccessListener {
                val cart = it.documents.mapNotNull { document ->
                    document.toObject(CartDataModels::class.java)?.apply { cartID = document.id }
                }
                trySend(ResultState.Success(cart))
            }.addOnFailureListener {
                trySend(ResultState.Error(it.toString()))
            }

        awaitClose { close() }

    }

    override fun getAllCategories(): Flow<ResultState<List<CategoryDataModels>>> = callbackFlow {

        trySend(ResultState.Loading)

        firebaseFirestore.collection("Category").get().addOnSuccessListener {
            val Category = it.documents.mapNotNull { document ->
                document.toObject(CategoryDataModels::class.java)
            }
            trySend(ResultState.Success(Category))

        }.addOnFailureListener {
            trySend(ResultState.Error(it.toString()))
        }
        awaitClose { close() }

    }


    override fun getCheckout(productId: String): Flow<ResultState<ProductDataModels>> =
        callbackFlow {
            trySend(ResultState.Loading)

            firebaseFirestore.collection("Products").document(productId).get()
                .addOnSuccessListener {
                    val product = it.toObject(ProductDataModels::class.java)
                    trySend(ResultState.Success(product!!))
                }.addOnFailureListener {
                    trySend(ResultState.Error(it.toString()))


                }

            awaitClose { close() }
        }


    override fun getBanner(): Flow<ResultState<List<BannerDataModels>>> = callbackFlow {
        trySend(ResultState.Loading)

        firebaseFirestore.collection("Banners").get().addOnSuccessListener {
            val banner = it.documents.mapNotNull { document ->
                document.toObject(BannerDataModels::class.java)
            }
            trySend(ResultState.Success(banner))
        }
            .addOnFailureListener {
                trySend(ResultState.Error(it.toString()))
            }

        awaitClose { close() }

    }


    override fun getSpecificCategoryItems(categoryName: String): Flow<ResultState<List<ProductDataModels>>> =
        callbackFlow {
            trySend(ResultState.Loading)

            firebaseFirestore.collection("Products").whereEqualTo("Category", categoryName).get()
                .addOnSuccessListener {

                    val products = it.documents.mapNotNull { document ->
                        document.toObject(ProductDataModels::class.java)?.apply {
                            productID = document.id
                        }
                    }
                    trySend(ResultState.Success(products))
                }.addOnFailureListener {
                    trySend(ResultState.Error(it.toString()))

                }
            awaitClose { close() }
        }

    override fun getAllSuggestedProduct(): Flow<ResultState<List<ProductDataModels>>> =
        callbackFlow {
            trySend(ResultState.Loading)
            firebaseFirestore.collection("Products").limit(5).get().addOnSuccessListener {

                val products = it.documents.mapNotNull { document ->
                    document.toObject(ProductDataModels::class.java)?.apply {
                        productID = document.id
                    }
                }
                trySend(ResultState.Success(products))
            }.addOnFailureListener {
                trySend(ResultState.Error(it.toString()))

            }


            awaitClose { close() }
        }
}









