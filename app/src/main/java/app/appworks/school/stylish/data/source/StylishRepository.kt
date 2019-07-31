package app.appworks.school.stylish.data.source

import androidx.lifecycle.LiveData
import app.appworks.school.stylish.data.*

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * Interface to the Stylish layers.
 */
interface StylishRepository {

    suspend fun getHotsList(): Result<List<HotsDataItem>>

    suspend fun getProductList(type: String, paging: String? = null): Result<ProductListProperty>

    suspend fun getUserProfile(token: String): Result<User>

    suspend fun userSignIn(fbToken: String): Result<UserSignInProperty>

    suspend fun postOrderCheckout(token: String, orderCheckoutDetail: OrderCheckoutDetail): Result<OrderCheckoutProperty>

    fun getProductsInCart(): LiveData<List<Product>>

    suspend fun isProductInCart(id: Long, colorCode: String, size: String): Boolean

    suspend fun insertProductInCart(product: Product)

    suspend fun updateProductInCart(product: Product)

    suspend fun removeProductInCart(id: Long, colorCode: String, size: String)

    suspend fun clearProductInCart()

    suspend fun getUserInformation(key: String?): String
}
