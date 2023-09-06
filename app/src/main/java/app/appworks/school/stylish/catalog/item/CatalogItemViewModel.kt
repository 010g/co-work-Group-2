package app.appworks.school.stylish.catalog.item

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import app.appworks.school.stylish.ABTestUUID
import app.appworks.school.stylish.ABTestVersion
import app.appworks.school.stylish.R
import app.appworks.school.stylish.StylishApplication
import app.appworks.school.stylish.catalog.CatalogTypeFilter
import app.appworks.school.stylish.component.GridSpacingItemDecoration
import app.appworks.school.stylish.data.Product
import app.appworks.school.stylish.util.Logger
import app.appworks.school.stylish.util.ServiceLocator.stylishRepository
import kotlinx.coroutines.launch

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * The [ViewModel] that is attached to the [CatalogItemFragment].
 */
class CatalogItemViewModel(
    catalogType: CatalogTypeFilter // Handle the type for each catalog item
) : ViewModel() {

    val catalog = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        config = PagingConfig(pageSize = 6),
        pagingSourceFactory = { PagingDataSource(catalogType) }
    ).flow.cachedIn(viewModelScope)

    // Handle navigation to detail
    private val _navigateToDetail = MutableLiveData<Product?>()

    val navigateToDetail: LiveData<Product?>
        get() = _navigateToDetail

    val decoration = GridSpacingItemDecoration(
        2,
        StylishApplication.instance.resources.getDimensionPixelSize(R.dimen.space_catalog_grid), true
    )

    init {
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]$this")
        Logger.i("------------------------------------")
    }

    fun navigateToDetail(product: Product) {
        _navigateToDetail.value = product
    }

    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }

    fun sendUserTrackingFromCatalogPageToProductDetailPage(productId : Long){
        Log.i("Elven login", "CatalogViewModel: sendUserTrackingFromCatalogPageToProductDetailPage API is called")
        viewModelScope.launch {
            Log.i("Elven login", "ABTestUUID.UUID = ${ABTestUUID.UUID}")
            Log.i("Elven login", "source = ${ABTestVersion.version}")
            Log.i("Elven login", "actionTo = $productId")
            StylishApplication.instance.stylishRepository.sendUserTracking(uuid = ABTestUUID.UUID, eventType = "visit", actionFrom = "CatalogPage", actionTo = "$productId", source = ABTestVersion.version )
            Log.i("Elven login", "CatalogViewModel: sendUserTrackingFromCatalogPageToProductDetailPage API finished")
        }
    }
}
