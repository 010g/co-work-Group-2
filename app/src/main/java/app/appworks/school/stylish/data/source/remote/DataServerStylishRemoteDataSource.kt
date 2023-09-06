package app.appworks.school.stylish.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import app.appworks.school.stylish.R
import app.appworks.school.stylish.data.CheckoutOrderResult
import app.appworks.school.stylish.data.HomeItem
import app.appworks.school.stylish.data.NativeSignInBody
import app.appworks.school.stylish.data.NativeSignUpBody
import app.appworks.school.stylish.data.OrderDetail
import app.appworks.school.stylish.data.Product
import app.appworks.school.stylish.data.ProductFavoriteListResult
import app.appworks.school.stylish.data.ProductListResult
import app.appworks.school.stylish.data.Result
import app.appworks.school.stylish.data.SingleUserProductFavoriteListResult
import app.appworks.school.stylish.data.SingleUserRecommendationListResult
import app.appworks.school.stylish.data.UUIDResult
import app.appworks.school.stylish.data.User
import app.appworks.school.stylish.data.UserSignInResult
import app.appworks.school.stylish.data.UserSignUpResult
import app.appworks.school.stylish.data.source.StylishDataSource
import app.appworks.school.stylish.network.DataServerStylishApi
import app.appworks.school.stylish.network.StylishApi
import app.appworks.school.stylish.util.Logger
import app.appworks.school.stylish.util.Util

object DataServerStylishRemoteDataSource : StylishDataSource {

    override suspend fun getMarketingHots(): Result<List<HomeItem>> {

        if (!Util.isInternetConnected()) {
            return Result.Fail(Util.getString(R.string.internet_not_connected))
        }

        return try {
            // this will run on a thread managed by Retrofit
            val listResult = DataServerStylishApi.retrofitService.getMarketingHots()

            listResult.error?.let {
                return Result.Fail(it)
            }
            Result.Success(listResult.toHomeItems())
        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            Result.Error(e)
        }
    }

    override suspend fun getProductList(type: String, paging: String?): Result<ProductListResult> {

        if (!Util.isInternetConnected()) {
            return Result.Fail(Util.getString(R.string.internet_not_connected))
        }

        return try {
            // this will run on a thread managed by Retrofit
            val listResult = DataServerStylishApi.retrofitService.getProductList(type = type, paging = paging)
            Log.i("elven test API","<getProductList> this api is call")

            listResult.error?.let {
                return Result.Fail(it)
            }
            Result.Success(listResult)
        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            Result.Error(e)
        }
    }

    override suspend fun getProductListWithFavorite(
        type: String,
        paging: String?,
        userId: Int?
    ): Result<ProductFavoriteListResult> {
        if (!Util.isInternetConnected()) {
            return Result.Fail(Util.getString(R.string.internet_not_connected))
        }

        return try {
            // this will run on a thread managed by Retrofit
            val listResult = DataServerStylishApi.retrofitService.getProductListWithFavorite(type = type, paging = paging, userId = userId)
            Log.i("elven test API","<getProductListWithFavorite> this api is call")

            listResult.error?.let {
                return Result.Fail(it)
            }
            Result.Success(listResult)
        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            Result.Error(e)
        }
    }

    override suspend fun getUserProfile(token: String): Result<User> {
        Log.i("elven test profile","DataServerStylishRemoteDataSource <getUserProfile> this api is call ")
        if (!Util.isInternetConnected()) {
            return Result.Fail(Util.getString(R.string.internet_not_connected))
        }

        return try {
            // this will run on a thread managed by Retrofit
            val listResult = DataServerStylishApi.retrofitService.getUserProfile(token)
            Log.i("elven test profile","<getUserProfile> this api is call")
            listResult.error?.let {
                return Result.Fail(it)
            }
            listResult.user?.let {
                return Result.Success(it)
            }
            Result.Fail(Util.getString(R.string.you_know_nothing))
        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            Result.Error(e)
        }
    }

    override suspend fun userSignIn(fbToken: String): Result<UserSignInResult> {

        if (!Util.isInternetConnected()) {
            return Result.Fail(Util.getString(R.string.internet_not_connected))
        }

        return try {
            // this will run on a thread managed by Retrofit
            val listResult = StylishApi.retrofitService.userSignIn(fbToken = fbToken)

            listResult.error?.let {
                return Result.Fail(it)
            }
            Result.Success(listResult)
        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            Result.Error(e)
        }
    }

    override suspend fun userSignIn(email: String, password: String): Result<UserSignInResult> {

        if (!Util.isInternetConnected()) {
            return Result.Fail(Util.getString(R.string.internet_not_connected))
        }

        return try {
            // this will run on a thread managed by Retrofit
            val listResult = DataServerStylishApi.retrofitService.userSignIn(
                NativeSignInBody(
                    email = email,
                    password = password
                )
            )

            listResult.error?.let {
                return Result.Fail(it)
            }
            Result.Success(listResult)
        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            Result.Error(e)
        }
    }

    override suspend fun userSignUp(name: String, email: String, password: String): Result<UserSignUpResult> {
        Log.i("Elven login", "userSignUp API is called")
        if (!Util.isInternetConnected()) {
            return Result.Fail(Util.getString(R.string.internet_not_connected))
        }

        return try {
            // this will run on a thread managed by Retrofit
            val listResult = DataServerStylishApi.retrofitService.userSignUp(
                NativeSignUpBody(
                    name = name,
                    email = email,
                    password = password
                )
            )

            listResult.error?.let {
                return Result.Fail(it)
            }
            Result.Success(listResult)
        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            Result.Error(e)
        }
    }

    override suspend fun insertProductToFavoriteList(userId: String, productId: Long) {
        Log.i("elven test API", "<insertProductToFavoriteList> API is call")
        try {
            DataServerStylishApi.retrofitService.insertProductToFavoriteList(userId,productId)
        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            Result.Error(e)
        }
    }

    override suspend fun deleteProductFromFavoriteList(userId: String, productId: Long) {
        Log.i("elven test API", "<deleteProductFromFavoriteList> API is call")
        try {
            DataServerStylishApi.retrofitService.deleteProductFromFavoriteList(userId,productId)
        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            Result.Error(e)
        }
    }

    override suspend fun getUserFavoriteList(userId: String): Result<SingleUserProductFavoriteListResult> {
        Log.i("Elven login", "<getUserFavoriteList> API is called")
        if (!Util.isInternetConnected()) {
            return Result.Fail(Util.getString(R.string.internet_not_connected))
        }

        return try {
            // this will run on a thread managed by Retrofit
            val listResult = DataServerStylishApi.retrofitService.getUserFavoriteList(
                userId
            )

            listResult.error?.let {
                return Result.Fail(it)
            }
            Result.Success(listResult)
        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            Result.Error(e)
        }
    }

    override suspend fun getUUID(): UUIDResult? {
        Log.i("Elven login", "Retrofit : <getUUID> API is called")

        return try {
            // this will run on a thread managed by Retrofit

            return DataServerStylishApi.retrofitService.getUUID()

        } catch (e: Exception) {
            Log.i("Elven login", "Exception : ${e}")
            return null
        }
    }

    override suspend fun sendUserTracking(
        uuid: String,
        eventType: String,
        actionFrom: String,
        actionTo: String,
        source: String
    ) {
        Log.i("elven login", "<sendUserTracking> API is call")
        try {
            DataServerStylishApi.retrofitService.sendUserTracking(uuid,
                eventType,
                actionFrom,
                actionTo,
                source)
        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            Result.Error(e)
        }
    }

    override suspend fun getRecommendation(userId: String): Result<SingleUserRecommendationListResult> {
        Log.i("Elven login", "<getRecommendation> API is called")
        if (!Util.isInternetConnected()) {
            return Result.Fail(Util.getString(R.string.internet_not_connected))
        }

        return try {
            // this will run on a thread managed by Retrofit
            val listResult = DataServerStylishApi.retrofitService.getRecommendation(
                userId
            )

            listResult.error?.let {
                return Result.Fail(it)
            }
            Result.Success(listResult)
        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            Result.Error(e)
        }
    }

    override suspend fun checkoutOrder(
        token: String,
        orderDetail: OrderDetail
    ): Result<CheckoutOrderResult> {

        if (!Util.isInternetConnected()) {
            return Result.Fail(Util.getString(R.string.internet_not_connected))
        }

        return try {
            // this will run on a thread managed by Retrofit
            val listResult = DataServerStylishApi.retrofitService.checkoutOrder(token, orderDetail)

            listResult.error?.let {
                return Result.Fail(it)
            }
            Result.Success(listResult)
        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            Result.Error(e)
        }
    }

    override fun getProductsInCart(): LiveData<List<Product>> {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun isProductInCart(id: Long, colorCode: String, size: String): Boolean {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun insertProductInCart(product: Product) {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun updateProductInCart(product: Product) {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun removeProductInCart(id: Long, colorCode: String, size: String) {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun clearProductInCart() {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }
}