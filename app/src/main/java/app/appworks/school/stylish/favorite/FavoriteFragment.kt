package app.appworks.school.stylish.favorite

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.snapshots.Snapshot.Companion.observe
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import app.appworks.school.stylish.NavigationDirections
import app.appworks.school.stylish.R
import app.appworks.school.stylish.data.Color
import app.appworks.school.stylish.data.Product
import app.appworks.school.stylish.data.Variant
import app.appworks.school.stylish.databinding.FragmentFavoriteBinding
import app.appworks.school.stylish.databinding.FragmentHomeBinding
import app.appworks.school.stylish.ext.getVmFactory
import app.appworks.school.stylish.home.HomeAdapter
import app.appworks.school.stylish.home.HomeViewModel


class FavoriteFragment : Fragment() {

    private val viewModel by viewModels<FavoriteViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val adapter = FavoriteListAdapter(FavoriteListAdapter.OnClickListener {
            viewModel.navigateToDetail(it)
        })
        binding.recyclerFavorite.adapter = adapter

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
                if (it!!.isEmpty()){
                    binding.textFavoriteNoProducts.visibility = View.VISIBLE
                }
                it?.let {
                    adapter.submitList(it)
                }
            }
        )


        return binding.root
    }

}