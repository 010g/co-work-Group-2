package app.appworks.school.stylish.recommend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.appworks.school.stylish.NavigationDirections
import app.appworks.school.stylish.R
import app.appworks.school.stylish.databinding.FragmentFavoriteBinding
import app.appworks.school.stylish.databinding.FragmentRecommendBinding
import app.appworks.school.stylish.ext.getVmFactory
import app.appworks.school.stylish.favorite.FavoriteListAdapter
import app.appworks.school.stylish.favorite.FavoriteViewModel


class RecommendFragment : Fragment() {

    private val viewModel by viewModels<RecommendViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRecommendBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val adapter = RecommendAdapter(RecommendAdapter.OnClickListener {
            viewModel.navigateToDetail(it)
        })
        binding.itemRecommendRecyclerview.adapter = adapter

        viewModel.navigateToDetail.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    findNavController().navigate(NavigationDirections.navigateToDetailFragment(it))
                    viewModel.onDetailNavigated()
                }
            }
        )

        viewModel.productListForAdapter.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    adapter.submitList(it)
                }
            }
        )


        return binding.root
    }









//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        val binding: FragmentRecommendBinding = DataBindingUtil.inflate(
//            inflater, R.layout.fragment_recommend, container, false
//        )
//
//
//        // adapter
//        val adapter = RecommendAdapter()
//        binding.itemRecommendRecyclerview.adapter = adapter
//
//        val itemList = listOf(
//            Item("Item 1"),
//            Item("Item 2"),
//            Item("Item 3"),
//            Item("Item 4"),
//            Item("Item 5")
//        )
//
//        adapter.submitList(itemList)
//
//        return binding.root
//    }
}