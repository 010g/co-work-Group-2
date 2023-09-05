package app.appworks.school.stylish.home

import android.app.Activity
import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.appworks.school.stylish.R
import app.appworks.school.stylish.data.HomeItem
import app.appworks.school.stylish.data.Product
import app.appworks.school.stylish.databinding.ItemHomeCollageBinding
import app.appworks.school.stylish.databinding.ItemHomeFullBinding
import app.appworks.school.stylish.databinding.ItemHomeTitleBinding
import app.appworks.school.stylish.databinding.ViewholderFullSectionBinding

/**
 * Created by Wayne Chen in Jul. 2019.
 *
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * [HomeItem], including computing diffs between lists.
 * @param onClickListener a lambda that takes the
 */
class HomeAdapter(
    private val onClickListener: OnClickListener,
    val activity: Activity,
    private val sendUserTrackingFromHomePageToProductDetailPage: (Long) -> Unit
) :
    ListAdapter<HomeItem, RecyclerView.ViewHolder>(DiffCallback) {
    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [Product]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [Product]
     */
    class OnClickListener(val clickListener: (product: Product) -> Unit) {
        fun onClick(product: Product) = clickListener(product)
    }

    class TitleViewHolder(private var binding: ItemHomeTitleBinding, val activity: Activity) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(title: String) {

            binding.title = title
            binding.root.visibility = View.GONE
            binding.executePendingBindings()
            activity.findViewById<TextView>(R.id.title_view).text = title
        }
    }

    class FullProductViewHolder(private var binding: ViewholderFullSectionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            product: Product,
            onClickListener: OnClickListener,
            sendUserTrackingFromHomePageToProductDetailPage: (Long) -> Unit
        ) {

            binding.product = product
            binding.root.setOnClickListener {
                onClickListener.onClick(product)
                sendUserTrackingFromHomePageToProductDetailPage(product.id)
            }
            binding.executePendingBindings()
        }
    }

    class CollageProductViewHolder(private var binding: ViewholderFullSectionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            product: Product,
            onClickListener: OnClickListener,
            sendUserTrackingFromHomePageToProductDetailPage: (Long) -> Unit
        ) {

            binding.product = product
            binding.root.setOnClickListener {
                onClickListener.onClick(product)
                sendUserTrackingFromHomePageToProductDetailPage(product.id)
            }
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<HomeItem>() {
        override fun areItemsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
            return oldItem.id == newItem.id
        }

        private const val ITEM_VIEW_TYPE_TITLE = 0x00
        private const val ITEM_VIEW_TYPE_PRODUCT_FULL = 0x01
        private const val ITEM_VIEW_TYPE_PRODUCT_COLLAGE = 0x02
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_TITLE -> TitleViewHolder(
                ItemHomeTitleBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ), activity
            )

            ITEM_VIEW_TYPE_PRODUCT_FULL -> FullProductViewHolder(
                ViewholderFullSectionBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            ITEM_VIEW_TYPE_PRODUCT_COLLAGE -> CollageProductViewHolder(
                ViewholderFullSectionBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is TitleViewHolder -> {
                holder.bind((getItem(position) as HomeItem.Title).title)
            }

            is FullProductViewHolder -> {
                holder.bind(
                    (getItem(position) as HomeItem.FullProduct).product, onClickListener,
                    sendUserTrackingFromHomePageToProductDetailPage
                )
            }

            is CollageProductViewHolder -> {
                holder.bind(
                    (getItem(position) as HomeItem.CollageProduct).product, onClickListener,
                    sendUserTrackingFromHomePageToProductDetailPage
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is HomeItem.Title -> ITEM_VIEW_TYPE_TITLE
            is HomeItem.FullProduct -> ITEM_VIEW_TYPE_PRODUCT_FULL
            is HomeItem.CollageProduct -> ITEM_VIEW_TYPE_PRODUCT_COLLAGE
        }
    }
}
