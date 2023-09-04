package app.appworks.school.stylish.profile

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import app.appworks.school.stylish.databinding.FragmentDetailOrderBinding

class DetailOrderFragment : Fragment() {

    private lateinit var binding: FragmentDetailOrderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve data from the bundle
        val orderHistoryItem = arguments?.getParcelable("orderHistory", OrderHistoryItem::class.java)
        Log.d("!!! orderHistoryItem", orderHistoryItem.toString())

        val detailOrderAdapter = DetailOrderAdapter()
        binding.recyclerViewOrders.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewOrders.adapter = detailOrderAdapter

        if (orderHistoryItem != null) {
            binding.orderNameString = orderHistoryItem.products[0].name
            binding.orderPhoneString = orderHistoryItem.products[0].phone
            binding.orderAddressString = orderHistoryItem.products[0].address
            binding.orderNumberString = orderHistoryItem.orderNumber
            binding.orderDateString = orderHistoryItem.orderDate
        }


        // Initialize your detailOrderItems list with data
        // For example:
        // var detailOrderItems = createAdapterData()
        // Add more items as needed
        // To update the data in your adapter, call submitDetailOrderList


        val detailOrderItemList = mutableListOf<DetailOrderItem>()
        if (orderHistoryItem != null) {
            for (item in orderHistoryItem.products){
                val main_image = item.main_image
                val title = item.title
                val color = item.color
                val size = item.size
                val qty = "x ${item.qty}"
                val price = item.price
                val detailOrderItem = DetailOrderItem(
                    main_image,
                    title,
                    color,
                    size,
                    price,
                    qty
                )
                detailOrderItemList.add(detailOrderItem)
            }
        }

        detailOrderAdapter.submitList(detailOrderItemList)

    }

}