package app.appworks.school.stylish.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


//Get API for order history fragment
@Parcelize
data class OrderHistoryResult(
    val histories: List<HistoryItem>
) : Parcelable

@Parcelize
data class HistoryItem(
    val order_id: String,
    val total: String,
    val time: String,
    val products: List<ProductItem>
) : Parcelable

@Parcelize
data class ProductItem(
    val name: String,
    val phone: String,
    val address: String,
    val main_image: String,
    val title: String,
    val color: String,
    val size: String,
    val qty: String,
    val price: String
) : Parcelable

//post API in the payment fragment
data class OrderHistoryRequest(
    val user_id: String,
    val history_item: HistoryItem
)