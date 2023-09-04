package app.appworks.school.stylish.profile

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.appworks.school.stylish.R
import app.appworks.school.stylish.data.HistoryItem
import app.appworks.school.stylish.data.ProductItem
import app.appworks.school.stylish.databinding.ItemOrderHistoryBinding
import com.bumptech.glide.Glide
import kotlinx.android.parcel.Parcelize

class OrderHistoryAdapter(private val onItemClick: (OrderHistoryItem) -> Unit) :
    ListAdapter<OrderHistoryItem, OrderHistoryAdapter.OrderHistoryViewHolder>(
        OrderHistoryDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemOrderHistoryBinding.inflate(inflater, parent, false)
        return OrderHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderHistoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class OrderHistoryViewHolder(private val binding: ItemOrderHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(orderHistoryItem: OrderHistoryItem) {
            binding.orderHistoryItem = orderHistoryItem
            binding.executePendingBindings()
            Glide.with(binding.imageOrderHistory.context)
                .load(orderHistoryItem.products[0].main_image)
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder) // Set placeholder if needed
                .into(binding.imageOrderHistory)

            // Set up click listener to navigate to DetailOrderFragment
            binding.root.setOnClickListener {
                onItemClick(orderHistoryItem)
            }
        }
    }
}

class OrderHistoryDiffCallback : DiffUtil.ItemCallback<OrderHistoryItem>() {
    override fun areItemsTheSame(oldItem: OrderHistoryItem, newItem: OrderHistoryItem): Boolean {
        return oldItem.orderNumber == newItem.orderNumber
    }

    override fun areContentsTheSame(oldItem: OrderHistoryItem, newItem: OrderHistoryItem): Boolean {
        return oldItem == newItem
    }
}

@Parcelize
data class OrderHistoryItem(
    val orderNumber: String,
    val orderPrice: String,
    val orderDate: String,
    val products: List<ProductItem>
) : Parcelable