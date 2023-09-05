package app.appworks.school.stylish.catalog.item

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import app.appworks.school.stylish.NativeLoginResult
import app.appworks.school.stylish.R
import app.appworks.school.stylish.StylishApplication
import app.appworks.school.stylish.catalog.CatalogTypeFilter
import app.appworks.school.stylish.data.Product
import app.appworks.school.stylish.data.ProductFavoriteListResult
import app.appworks.school.stylish.data.ProductListResult
import app.appworks.school.stylish.data.Result
import app.appworks.school.stylish.data.source.remote.StylishRemoteDataSource.getProductList
import app.appworks.school.stylish.network.DataServerStylishApi
import app.appworks.school.stylish.network.DataServerStylishApiService
import app.appworks.school.stylish.network.StylishApiService
import app.appworks.school.stylish.util.Util.getString

/**
 * Created by Wayne Chen in Jul. 2019.
 */
class PagingDataSource(val type: CatalogTypeFilter) : PagingSource<String, Product>() {

    override fun getRefreshKey(state: PagingState<String, Product>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.toInt()?.plus(1)?.toString() ?: anchorPage?.nextKey?.toInt()
                ?.minus(1)?.toString()
        }
    }


    override suspend fun load(params: LoadParams<String>): LoadResult<String, Product> {
        return try {
            val result= StylishApplication.instance.stylishRepository
                    .getProductList(type = type.value, paging = params.key)

            // Start refresh at page 1 if undefined.
            return when (result) {
                is Result.Success -> {
                    result.data.products?.let {
                        LoadResult.Page(
                            data = it,
                            prevKey = null, // Only paging forward.
                            nextKey = result.data.paging
                        )
                    }
                        ?: throw java.lang.Exception(getString(R.string.you_know_nothing)) //TODO: change to API issue, 200 without data
                }

                is Result.Fail -> {
                    throw java.lang.Exception(result.error)
//                    _errorInitialLoad.value = result.error
//                    _statusInitialLoad.value = LoadApiStatus.ERROR
                }

                is Result.Error -> {
                    throw java.lang.Exception(result.exception.toString())
//                    _errorInitialLoad.value = result.exception.toString()
//                    _statusInitialLoad.value = LoadApiStatus.ERROR
                }

                else -> {
                    throw java.lang.Exception(getString(R.string.you_know_nothing))
//                    _errorInitialLoad.value = getString(R.string.you_know_nothing)
//                    _statusInitialLoad.value = LoadApiStatus.ERROR
                }
            }
        } catch (e: Exception) {
            // Handle errors in this block and return LoadResult.Error if it is an
            // expected error (such as a network failure).
            LoadResult.Error(e)
        }
    }
}
