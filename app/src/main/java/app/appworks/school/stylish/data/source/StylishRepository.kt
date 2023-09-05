package app.appworks.school.stylish.data.source

import androidx.lifecycle.LiveData
import app.appworks.school.stylish.data.*
import retrofit2.http.Query

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * Interface to the Stylish layers.
 */
interface StylishRepository {

    suspend fun getMarketingHots(): Result<List<HomeItem>>

    suspend fun getProductList(type: String, paging: String? = null): Result<ProductListResult>

    suspend fun getProductListWithFavorite(
        type: String,
        paging: String? = null,
        userId: Int?
    ): Result<ProductFavoriteListResult>

    suspend fun getUserProfile(token: String): Result<User>

    suspend fun userSignIn(fbToken: String): Result<UserSignInResult>

    suspend fun userSignIn(email: String, password: String): Result<UserSignInResult>

    suspend fun userSignUp(name: String, email: String, password: String): Result<UserSignUpResult>

    suspend fun insertProductToFavoriteList(userId: String, productId: Long)

    suspend fun deleteProductFromFavoriteList(userId: String, productId: Long)

    suspend fun getUserFavoriteList(userId: String) : Result<SingleUserProductFavoriteListResult>

    suspend fun getUUID() : UUIDResult?

    suspend fun checkoutOrder(token: String, orderDetail: OrderDetail): Result<CheckoutOrderResult>

    fun getProductsInCart(): LiveData<List<Product>>

    suspend fun isProductInCart(id: Long, colorCode: String, size: String): Boolean

    suspend fun insertProductInCart(product: Product)

    suspend fun updateProductInCart(product: Product)

    suspend fun removeProductInCart(id: Long, colorCode: String, size: String)

    suspend fun clearProductInCart()
}























