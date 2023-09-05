package app.appworks.school.stylish.recommend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.appworks.school.stylish.data.Product
import app.appworks.school.stylish.databinding.ItemRecommendBinding


class RecommendAdapter(val onClickListener: OnClickListener) :
    ListAdapter<Product, RecommendAdapter.RecommendViewHolder>(DiffCallback) {

    class RecommendViewHolder(private var binding: ItemRecommendBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.product = product
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [Product]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommendViewHolder {
        return RecommendViewHolder(ItemRecommendBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: RecommendViewHolder, position: Int) {
        val product = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(product)
        }
        holder.bind(product)
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [Product]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [Product]
     */
    class OnClickListener(val clickListener: (product: Product) -> Unit) {
        fun onClick(product: Product) = clickListener(product)
    }
}





//class RecommendAdapter() : ListAdapter<Item, RecyclerView.ViewHolder>(ProductDiffCallback()) {
//
//    class RecommendViewHolder(private val binding: ItemRecommendBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(dataItem: Item) {
//            binding.property = dataItem
//            binding.executePendingBindings()
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        val view=ItemRecommendBinding.inflate(
//            LayoutInflater.from(parent.context),
//            parent,
//            false
//        )
//        return RecommendViewHolder(view)
//
//    }
//
////    override fun onBindViewHolder(holder: RecommendViewHolder, position: Int) {
////        val dataItem = getItem(position)
////        holder.bind(dataItem)
////    }
//
//override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//    val dataItem = getItem(position)
//    (holder as RecommendViewHolder).bind(dataItem)
//}
//}
//
//private class ProductDiffCallback() : DiffUtil.ItemCallback<Item>() {
//    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
//        return oldItem == newItem
//    }
//
//    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
//        return oldItem == newItem
//    }
//}

