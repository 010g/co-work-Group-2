package app.appworks.school.stylish.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.appworks.school.stylish.NativeLoginResult
import app.appworks.school.stylish.data.HistoryItem
import app.appworks.school.stylish.data.OrderHistoryResult
import app.appworks.school.stylish.data.ProductItem
import app.appworks.school.stylish.databinding.FragmentOrderHistoryBinding
import app.appworks.school.stylish.network.DataServerStylishApi
import app.appworks.school.stylish.network.StylishApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderHistoryFragment : Fragment() {

    private lateinit var binding: FragmentOrderHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up the RecyclerView and adapter
        val recyclerView: RecyclerView = binding.recyclerViewOrderHistory
        recyclerView.layoutManager = LinearLayoutManager(context)
        val orderHistoryAdapter = OrderHistoryAdapter { item ->
            // Handle item click and navigate to DetailOrderFragment
            navigateToDetailOrderFragment(item)
        }

        recyclerView.adapter = orderHistoryAdapter


        GlobalScope.launch(Dispatchers.Main) {
            try {
                val orderHistoryData = withContext(Dispatchers.IO) {
                    DataServerStylishApi.retrofitService.getOrderHistory(userId=NativeLoginResult.nativeId.toString())
                }
                val orderHistoryItemList = mutableListOf<OrderHistoryItem>()
                for (item in orderHistoryData.histories){
                    val orderHistoryItem = OrderHistoryItem(
                        "訂單編號：" + item.order_id,
                        "訂單金額：" + item.total,
                        "訂購日期：" + item.time,
                        item.products
                    )
                    orderHistoryItemList.add(orderHistoryItem)
                }

                orderHistoryAdapter.submitList(orderHistoryItemList)
            } catch (e: Exception) {
                // Handle API call errors here
            }
        }



    }

    private fun navigateToDetailOrderFragment(orderHistoryItem: OrderHistoryItem) {

        // Navigate to DetailOrderFragment using the action defined in navigation.xml
        findNavController().navigate(OrderHistoryFragmentDirections.navigateToDetailOrderFragment(orderHistoryItem))
    }

    // Function to create sample order history data (replace with your data source)
    private fun createSampleOrderHistoryData(): OrderHistoryResult {
        val productItem1 = createProductItem("Product 1", "123-456-7890", "Address 1", "image1.jpg", "Title 1", "C0C0C0", "S", "2", "20.0")
        val productItem2 = createProductItem("Product 2", "987-654-3210", "Address 2", "image2.jpg", "Title 2", "FFFFFF", "M", "1", "30.0")

        val historyItem1 = createHistoryItem("Order1", "100.0", "2023-09-02", listOf(productItem1, productItem2))
        val historyItem2 = createHistoryItem("Order2", "50.0", "2023-09-01", listOf(productItem1))
        val historyItem3 = createHistoryItem("Order3", "80.0", "2023-09-05", listOf(productItem2))

        val historyItems = listOf(historyItem1, historyItem2, historyItem3)

        return OrderHistoryResult(historyItems)
    }

    private fun createProductItem(
        name: String,
        phone: String,
        address: String,
        mainImage: String,
        title: String,
        color: String,
        size: String,
        qty: String,
        price: String
    ): ProductItem {
        return ProductItem(name, phone, address, mainImage, title, color, size, qty, price)
    }

    private fun createHistoryItem(
        orderId: String,
        total: String,
        time: String,
        productItems: List<ProductItem>
    ): HistoryItem {
        return HistoryItem(orderId, total, time, productItems)
    }

}

