package app.appworks.school.stylish.detail

import android.graphics.Rect
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import app.appworks.school.stylish.ABTestUUID
import app.appworks.school.stylish.ABTestVersion
import app.appworks.school.stylish.NativeLoginResult
import app.appworks.school.stylish.R
import app.appworks.school.stylish.StylishApplication
import app.appworks.school.stylish.data.Product
import app.appworks.school.stylish.data.Result
import app.appworks.school.stylish.data.source.StylishRepository
import app.appworks.school.stylish.data.succeeded
import app.appworks.school.stylish.util.Logger
import app.appworks.school.stylish.util.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * The [ViewModel] that is attached to the [DetailFragment].
 */
class DetailViewModel(
    private val stylishRepository: StylishRepository,
    private val arguments: Product
) : ViewModel() {

    // Detail has product data from arguments
    private val _product = MutableLiveData<Product>().apply {
        value = arguments
    }

    val product: LiveData<Product>
        get() = _product

    val productSizesText: LiveData<String> = product.map {
        when (it.sizes.size) {
            0 -> ""
            1 -> it.sizes.first()
            else -> StylishApplication.instance.getString(R.string._dash_, it.sizes.first(), it.sizes.last())
        }
    }

    // it for gallery circles design
    private val _snapPosition = MutableLiveData<Int>()

    val snapPosition: LiveData<Int>
        get() = _snapPosition

    // Handle navigation to Add2cart
    private val _navigateToAdd2cart = MutableLiveData<Product>()

    val navigateToAdd2cart: LiveData<Product>
        get() = _navigateToAdd2cart

    // Handle leave detail
    private val _leaveDetail = MutableLiveData<Boolean>()

    val leaveDetail: LiveData<Boolean>
        get() = _leaveDetail

    private var _isFavorite = MutableLiveData<Boolean>()

    val isFavorite:LiveData<Boolean>
        get() = _isFavorite

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val decoration = object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.left = 0
            } else {
                outRect.left = StylishApplication.instance.resources.getDimensionPixelSize(R.dimen.space_detail_circle)
            }
        }
    }

    private fun checkIsFavoriteOrNot(){
        viewModelScope.launch {
            try{
                for (page in 0..2){
                    val result = stylishRepository.getProductListWithFavorite(type = "all", paging = page.toString(), userId = NativeLoginResult.nativeId)
                    when (result) {
                        is Result.Success -> {
                            result.data.products?.forEach {
                                if (_product.value?.id == it.id){
                                    _isFavorite.value = it.favorite
                                    Log.i("elven test API", "favorite : ${_isFavorite.value}")
                                }
                            }
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
                }
                Log.i("elven test API", "-------------------------------------------------")
            } catch (e:Exception){
                Log.i("elven test API", "Fail to call function")
            }
        }
    }



    /**
     * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the
     * Retrofit service to stop.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]$this")
        Logger.i("------------------------------------")

        // Todo checking product's isFavorite value
        checkIsFavoriteOrNot()
    }

    /**
     * When the gallery scroll, at the same time circles design will switch.
     */
    fun onGalleryScrollChange(
        layoutManager: RecyclerView.LayoutManager?,
        linearSnapHelper: LinearSnapHelper
    ) {
        val snapView = linearSnapHelper.findSnapView(layoutManager)
        snapView?.let {
            layoutManager?.getPosition(snapView)?.let {
                if (it != snapPosition.value) {
                    _snapPosition.value = it
                }
            }
        }
    }

    fun navigateToAdd2cart(product: Product) {
        _navigateToAdd2cart.value = product
    }

    fun onAdd2cartNavigated() {
        _navigateToAdd2cart.value = null
    }

    fun leaveDetail() {
        _leaveDetail.value = true
    }

    fun addToFavorite(){
        if (NativeLoginResult.nativeId != -1){
            viewModelScope.launch {
                try {
                    stylishRepository.insertProductToFavoriteList(NativeLoginResult.nativeId.toString(),_product.value!!.id)
                    Log.i("elven test API", "insert success")
                } catch (e:Exception){
                    Log.i("elven test API", "Fail to insert Favorite")
                }
            }
        }

        _isFavorite.value = true
    }

    fun removeFromFavorite(){
        if (NativeLoginResult.nativeId != -1){
            viewModelScope.launch {
                try {
                    stylishRepository.deleteProductFromFavoriteList(NativeLoginResult.nativeId.toString(),_product.value!!.id)
                    Log.i("elven test API", "delete success")
                } catch (e:Exception){
                    Log.i("elven test API", "Fail to delete Favorite")
                }
            }
        }

        _isFavorite.value = false
    }

    fun sendUserTrackingFromDetailPageToLastPage(lastPage:String){
        Log.i("Elven login", "DetailViewModel: sendUserTrackingFromDetailPageToLastPage API is called")
        viewModelScope.launch {
            Log.i("Elven login", "ABTestUUID.UUID = ${ABTestUUID.UUID}")
            Log.i("Elven login", "source = ${ABTestVersion.version}")
            Log.i("Elven login", "actionFrom = ${_product.value!!.id}")
            Log.i("Elven login", "actionTo = $lastPage")
            stylishRepository.sendUserTracking(uuid = ABTestUUID.UUID, eventType = "visit", actionFrom = "${_product.value!!.id}", actionTo = "$lastPage", source = ABTestVersion.version )
            Log.i("Elven login", "ProfileViewModel: sendUserTrackingFromDetailPageToLastPage API finished")
        }
    }
}
