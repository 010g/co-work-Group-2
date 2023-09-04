package app.appworks.school.stylish.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.appworks.school.stylish.R
import app.appworks.school.stylish.databinding.ItemDetailOrderBinding
import com.bumptech.glide.Glide

class DetailOrderAdapter :
    ListAdapter<DetailOrderItem, DetailOrderAdapter.DetailOrderViewHolder>(DetailOrderDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailOrderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDetailOrderBinding.inflate(inflater, parent, false)
        return DetailOrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailOrderViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    inner class DetailOrderViewHolder(private val binding: ItemDetailOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(detailOrderItem: DetailOrderItem) {
            Glide.with(binding.imageDetailOrder.context)
                .load(detailOrderItem.imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder) // Set placeholder if needed
                .into(binding.imageDetailOrder)
            binding.detailOrderItem = detailOrderItem
            binding.executePendingBindings()
        }
    }
}

class DetailOrderDiffCallback : DiffUtil.ItemCallback<DetailOrderItem>() {
    override fun areItemsTheSame(oldItem: DetailOrderItem, newItem: DetailOrderItem): Boolean {
        return oldItem.title == newItem.title // Change this to your unique identifier
    }

    override fun areContentsTheSame(oldItem: DetailOrderItem, newItem: DetailOrderItem): Boolean {
        return oldItem == newItem
    }
}

data class DetailOrderItem(
    val imageUrl: String,
    val title: String,
    val color: String,
    val size: String,
    val price: String,
    val amount: String
)