package app.appworks.school.stylish.network

import app.appworks.school.stylish.data.CheckoutOrderResult
import app.appworks.school.stylish.data.MarketingHotsResult
import app.appworks.school.stylish.data.NativeSignInBody
import app.appworks.school.stylish.data.NativeSignUpBody
import app.appworks.school.stylish.data.OrderDetail
import app.appworks.school.stylish.data.OrderHistoryResult
import app.appworks.school.stylish.data.ProductFavoriteListResult
import app.appworks.school.stylish.data.ProductListResult
import app.appworks.school.stylish.data.SingleUserProductFavoriteListResult
import app.appworks.school.stylish.data.UUIDResult
import app.appworks.school.stylish.data.UserProfileResult
import app.appworks.school.stylish.data.UserSignInResult
import app.appworks.school.stylish.data.UserSignUpResult
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.lang.reflect.Parameter

private const val BASE_URL = "http://52.64.246.19:8080/api/1.0/"

val dataServerMoshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(dataServerMoshi))
    .baseUrl(BASE_URL)
    .build()

interface DataServerStylishApiService {
    // call when user is not login
    @GET("products/{catalogType}")
    suspend fun getProductList(
        @Path("catalogType") type: String,
        @Query("paging") paging: String? = null
    ): ProductListResult

    // call when user is login
    @GET("products/{catalogType}")
    suspend fun getProductListWithFavorite(
        @Path("catalogType") type: String,
        @Query("paging") paging: String? = null,
        @Query("user_id") userId: Int? = null
    ): ProductFavoriteListResult


    @GET("marketing/hots")
    suspend fun getMarketingHots(): MarketingHotsResult


    @FormUrlEncoded
    @POST("user/signin")
    suspend fun userSignIn(
        @Field("provider") provider: String = "facebook",
        @Field("access_token") fbToken: String
    ): UserSignInResult

    @POST("signin")
    suspend fun userSignIn(
        @Body nativeSignInBody: NativeSignInBody
    ): UserSignInResult

    @POST("signup")
    suspend fun userSignUp(
        @Body nativeSignUpBody: NativeSignUpBody
    ): UserSignUpResult

    @GET("profile")
    suspend fun getUserProfile(@Header("Authorization") token: String): UserProfileResult

    //order history (need to be revise)
    @GET("orderHistory")
    suspend fun getOrderHistory(@Query("user_id") userId: String): OrderHistoryResult


    @POST("order/checkout")
    suspend fun checkoutOrder(
        @Header("Authorization") token: String,
        @Body orderDetail: OrderDetail
    ): CheckoutOrderResult

    // add product to favorite list
    @GET("product/favorite/insert_fav")
    suspend fun insertProductToFavoriteList(
        @Query("user_id") userId: String,
        @Query("fav_product_id") productId: Long
    )

    // delete product from favorite list
    @GET("product/favorite/delete_fav")
    suspend fun deleteProductFromFavoriteList(
        @Query("user_id") userId: String,
        @Query("fav_product_id") productId: Long
    )

    // get user favorite list
    @GET("product/favorite/get_fav")
    suspend fun getUserFavoriteList(
        @Query("user_id") userId: String? = null
    ): SingleUserProductFavoriteListResult

    // get user AB test own UUID
    @GET("uuid")
    suspend fun getUUID(): UUIDResult?

    // send user tracking behavior API
    @GET("tracking")
    suspend fun sendUserTracking(
        @Query("uuid") uuid: String,
        @Query("event_type") eventType: String,
        @Query("action_from") actionFrom: String,
        @Query("action_to") actionTo: String,
        @Query("source") source: String
    )

}

object DataServerStylishApi {
    val retrofitService: DataServerStylishApiService by lazy {
        retrofit.create(
            DataServerStylishApiService::class.java
        )
    }
}