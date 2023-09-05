package app.appworks.school.stylish.favorite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.appworks.school.stylish.NativeLoginResult
import app.appworks.school.stylish.R
import app.appworks.school.stylish.data.Color
import app.appworks.school.stylish.data.Product
import app.appworks.school.stylish.data.ProductFavorite
import app.appworks.school.stylish.data.Result
import app.appworks.school.stylish.data.Variant
import app.appworks.school.stylish.data.source.StylishRepository
import app.appworks.school.stylish.util.Util
import kotlinx.coroutines.launch

class FavoriteViewModel(private val stylishRepository: StylishRepository) : ViewModel() {
    private val _navigateToDetail = MutableLiveData<Product>()

    val navigateToDetail: LiveData<Product>
        get() = _navigateToDetail

    private val _productListForAdapter = MutableLiveData<List<Product>?>()

    val productListForAdapter: LiveData<List<Product>?>
        get() = _productListForAdapter



    fun navigateToDetail(product: Product) {
        _navigateToDetail.value = product
    }

    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }

    private fun getUserFavoriteList(){
        viewModelScope.launch {
            try {
                val result = stylishRepository.getUserFavoriteList(NativeLoginResult.nativeId.toString())
                when (result) {
                    is Result.Success -> {
                        _productListForAdapter.value = result.data.products
                        Log.i("elven test API", "data is good, ready to submit!")
                    }
                    is Result.Fail -> {
                        Log.i("elven test API", "Fail")
                        throw java.lang.Exception(result.error)
                    }
                    is Result.Error -> {
                        Log.i("elven test API", "Error")
                        throw java.lang.Exception(result.exception.toString())
                    }
                    else -> {
                        Log.i("elven test API", "else")
                        throw java.lang.Exception(Util.getString(R.string.you_know_nothing))
                    }
                }
            } catch (e:Exception){
                Log.i("elven test API", "Fail to show Favorite list")
            }
        }
    }

    init {
        getUserFavoriteList()
//        val dataList = mutableListOf<Product>()
//        val item1 = Product(
//            1,
//            "mockTitle",
//            "mockDescription",
//            878787,
//            "mockTexture",
//            "mockWash",
//            "mockPlace",
//            "mockNote",
//            "mockStory",
//            listOf(Color("巴拉八八八","CCCCCC")),
//            listOf("1","2"),
//            listOf(Variant("FFFFFF", "mockSize", 8787)),
//            "https://api.appworks-school.tw/assets/201807201824/main.jpg",
//            listOf("8", "7")
//        )
//        dataList.add(item1)
//        dataList.add(item1)
//        dataList.add(item1)
//        dataList.add(item1)
//        dataList.add(item1)
//
//        _productListForAdapter.value = dataList
    }

}