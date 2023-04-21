package app.appworks.school.stylish.catalog.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import app.appworks.school.stylish.NavigationDirections
import app.appworks.school.stylish.catalog.CatalogTypeFilter
import app.appworks.school.stylish.databinding.FragmentCatalogItemBinding
import app.appworks.school.stylish.ext.collect
import app.appworks.school.stylish.ext.getVmFactory
import app.appworks.school.stylish.network.LoadApiStatus
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * Created by Wayne Chen in Jul. 2019.
 */
class CatalogItemFragment(private val catalogType: CatalogTypeFilter) : Fragment() {

    /**
     * Lazily initialize our [CatalogItemViewModel].
     */
    private val viewModel by viewModels<CatalogItemViewModel> { getVmFactory(catalogType) }

    lateinit var adapter: PagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentCatalogItemBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel

        adapter = PagingAdapter(
            PagingAdapter.OnClickListener {
                viewModel.navigateToDetail(it)
            }
        )

        viewLifecycleOwner.collect(
            flow = adapter.loadStateFlow
                .distinctUntilChangedBy { it.source.refresh }
                .map { it.refresh },
            action = {
                binding.executePendingBindings()

                if(it != LoadState.Loading) {
                    binding.layoutSwipeRefreshCatalogItem.isRefreshing = false
                }
                binding.uiState = when(it) {
                    is LoadState.Error -> {
                        binding.errMessage = it.error.message
                        LoadApiStatus.ERROR
                    }
                    is LoadState.Loading -> LoadApiStatus.LOADING
                    else -> LoadApiStatus.DONE
                }
            }
        )

        binding.recyclerCatalogItem.adapter = adapter

        viewModel.navigateToDetail.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    findNavController().navigate(NavigationDirections.navigateToDetailFragment(it))
                    viewModel.onDetailNavigated()
                }
            }
        )
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.catalog.collectLatest {
                    (binding.recyclerCatalogItem.adapter as PagingAdapter).submitData(it)
                }
            }
        }

        binding.layoutSwipeRefreshCatalogItem.setOnRefreshListener {
            binding.layoutSwipeRefreshCatalogItem.isRefreshing = true
            (binding.recyclerCatalogItem.adapter as PagingAdapter).refresh()
        }

        return binding.root
    }
}
