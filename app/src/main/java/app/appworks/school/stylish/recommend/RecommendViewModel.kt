package app.appworks.school.stylish.recommend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.appworks.school.stylish.data.Color
import app.appworks.school.stylish.data.Product
import app.appworks.school.stylish.data.Variant
import app.appworks.school.stylish.data.source.StylishRepository

class RecommendViewModel(private val stylishRepository: StylishRepository) : ViewModel(){

    private val _navigateToDetail = MutableLiveData<Product>()

    val navigateToDetail: LiveData<Product>
        get() = _navigateToDetail

    private val _productListForAdapter = MutableLiveData<List<Product>>()

    val productListForAdapter: LiveData<List<Product>>
        get() = _productListForAdapter



    fun navigateToDetail(product: Product) {
        _navigateToDetail.value = product
    }

    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }

    init {
        // TODO: get api from dataServer (getFavoriteCollection)

        // TODO: Give the value to _navigateToDetail (if no need to modify content)
        val dataList = mutableListOf<Product>()
        val item1 = Product(
            1,
            "mockTitle",
            "mockDescription",
            878787,
            "mockTexture",
            "mockWash",
            "mockPlace",
            "mockNote",
            "mockStory",
            listOf(Color("巴拉八八八","CCCCCC")),
            listOf("1","2"),
            listOf(Variant("FFFFFF", "mockSize", 8787)),
            "https://api.appworks-school.tw/assets/201807201824/main.jpg",
            listOf("8", "7")
        )
        dataList.add(item1)
        dataList.add(item1)
        dataList.add(item1)
        dataList.add(item1)
        dataList.add(item1)

        _productListForAdapter.value = dataList
    }
}