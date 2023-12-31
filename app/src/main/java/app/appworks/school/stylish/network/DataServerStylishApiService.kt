package app.appworks.school.stylish.network

import app.appworks.school.stylish.data.MarketingHotsResult
import app.appworks.school.stylish.data.NativeSignInBody
import app.appworks.school.stylish.data.NativeSignUpBody
import app.appworks.school.stylish.data.OrderHistoryResult
import app.appworks.school.stylish.data.ProductListResult
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
    @GET("products/{catalogType}")
    suspend fun getProductList(
        @Path("catalogType") type: String,
        @Query("paging") paging: String? = null
    ): ProductListResult


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
}

object DataServerStylishApi {
    val retrofitService: DataServerStylishApiService by lazy { retrofit.create(
        DataServerStylishApiService::class.java) }
}