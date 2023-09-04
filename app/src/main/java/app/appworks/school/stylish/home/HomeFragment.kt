package app.appworks.school.stylish.home

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import app.appworks.school.stylish.ABTestVersion
import app.appworks.school.stylish.NavigationDirections
import app.appworks.school.stylish.data.Color
import app.appworks.school.stylish.data.Product
import app.appworks.school.stylish.data.Variant
import app.appworks.school.stylish.databinding.FragmentHomeBinding
import app.appworks.school.stylish.ext.getVmFactory

/**
 * Created by Wayne Chen in Jul. 2019.
 */
class HomeFragment : Fragment() {

    /**
     * Lazily initialize our [HomeViewModel].
     */
    private val viewModel by viewModels<HomeViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        init()
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        val activity = requireActivity()

        if(ABTestVersion.version == "A"){
            binding.constraint1.visibility = View.GONE
            binding.constraint2.visibility = View.VISIBLE
        } else if (ABTestVersion.version == "B"){
            binding.constraint1.visibility = View.VISIBLE
            binding.constraint2.visibility = View.GONE
        }

        binding.recyclerHome.adapter = HomeAdapter(
            HomeAdapter.OnClickListener {
                viewModel.navigateToDetail(it)
            }, activity
        )
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerHome)

        //--^--//
        binding.recyclerHome2.adapter = HomeOriginalAdapter(
            HomeOriginalAdapter.OnClickListener {
                viewModel.navigateToDetail(it)
            }
        )
        //--v--//

        binding.layoutSwipeRefreshHome.setOnRefreshListener {
            viewModel.refresh()
        }
        //--^--//
        binding.layoutSwipeRefreshHome2.setOnRefreshListener {
            viewModel.refresh()
        }
        //--v--//

        viewModel.refreshStatus.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    binding.layoutSwipeRefreshHome.isRefreshing = it
                    //--^--//
                    binding.layoutSwipeRefreshHome2.isRefreshing = it
                    //--v--//
                }
            }
        )




        viewModel.navigateToDetail.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    findNavController().navigate(NavigationDirections.navigateToDetailFragment(it))
                    viewModel.onDetailNavigated()
                }
            }
        )

        return binding.root
    }

//    private fun init() {
//        activity?.let {
//            ViewModelProviders.of(it).get(MainViewModel::class.java).apply {
//                currentFragmentType.value = CurrentFragmentType.HOME
//            }
//        }
//    }
}
