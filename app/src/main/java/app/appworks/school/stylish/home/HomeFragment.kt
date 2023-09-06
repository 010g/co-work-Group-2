package app.appworks.school.stylish.home

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.snapshots.Snapshot.Companion.observe
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import app.appworks.school.stylish.ABTestUUID
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
        Log.i("elven test API", "HomeFragment inflated")
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        val activity = requireActivity()

        //-----//
        // decide to ask AB test uuid or not
        val uuidSharePreference = context?.getSharedPreferences(
            "for_user_abtest",
            Context.MODE_PRIVATE
        )
        val getUuidWhenFirstLaunch = uuidSharePreference!!.getString("uuid", "")
        Log.i("elven test API", "getUuidWhenFirstLaunch: $getUuidWhenFirstLaunch")



        if (getUuidWhenFirstLaunch == "") {
            Log.i("elven test API", "Open this App first time, so let's get UUID")
            viewModel.getUUID()
//            viewModel.getABVersion()
            Log.i("elven test API", "Open this App first time, successfully got UUID")
        } else {
            Log.i("elven test API", "Open this App after 1st time, let's get from sharePreference")
            ABTestUUID.UUID = uuidSharePreference.getString("uuid", "no uuid!").toString()
            ABTestVersion.version = uuidSharePreference.getString("ab_version", "A").toString()
            viewModel.getABVersion()
            Log.i("elven test API", "ABTestUUID.UUID: ${ABTestUUID.UUID}")
            Log.i("elven test API", "ABTestVersion.version: ${ABTestVersion.version}")
        }

        viewModel.UUID.observe(viewLifecycleOwner, Observer {
            uuidSharePreference.edit().putString("uuid", it).apply()
            ABTestUUID.UUID = it!!
            Log.i(
                "elven test API",
                "Just after call getUUID function, and ABTestUUID.UUID = ${ABTestUUID.UUID}"
            )
        })

        viewModel.ABVersion.observe(viewLifecycleOwner, Observer {
            uuidSharePreference.edit().putString("ab_version", it).apply()
            ABTestVersion.version = it!!
            Log.i(
                "elven test API",
                "Just after call getUUID function, and ABTestVersion.version = ${ABTestVersion.version}"
            )

            if (ABTestVersion.version == "A") {
                binding.constraint1.visibility = View.GONE
                binding.constraint2.visibility = View.VISIBLE
            } else if (ABTestVersion.version == "B") {
                binding.constraint1.visibility = View.VISIBLE
                binding.constraint2.visibility = View.GONE
            }

            if (ABTestVersion.limitForHomePageFirstUserTrackingApiCall == 1) {
                Log.i("Elven login", "--------------------------------------------------")
                Log.i(
                    "Elven login",
                    "HomeFragment: sendUserTrackingWhenUserLoginFirstTime API is ready to call"
                )
                viewModel.sendUserTrackingWhenUserLoginFirstTime()
                ABTestVersion.limitForHomePageFirstUserTrackingApiCall++
                Log.i(
                    "Elven login",
                    "ABTestVersion.limitForHomePageFirstUserTrackingApiCall = ${ABTestVersion.limitForHomePageFirstUserTrackingApiCall}"
                )
            }
        })
        //-----//

//        viewModel.ABTestVersionx.observe(viewLifecycleOwner, Observer {
//            if (ABTestVersion.version == "A") {
//                binding.constraint1.visibility = View.GONE
//                binding.constraint2.visibility = View.VISIBLE
//            } else if (ABTestVersion.version == "B") {
//                binding.constraint1.visibility = View.VISIBLE
//                binding.constraint2.visibility = View.GONE
//            }
//        })

        binding.recyclerHome.adapter = HomeAdapter(
            HomeAdapter.OnClickListener {
                viewModel.navigateToDetail(it)
            }, activity,
            viewModel.sendUserTrackingFromHomePageToProductDetailPage
        )
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerHome)

        //--^--//
        binding.recyclerHome2.adapter = HomeOriginalAdapter(
            HomeOriginalAdapter.OnClickListener {
                viewModel.navigateToDetail(it)
            },
            viewModel.sendUserTrackingFromHomePageToProductDetailPage
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

        fun showVersionA() {
            binding.constraint1.visibility = View.GONE
            binding.constraint2.visibility = View.VISIBLE
        }

        fun showVersionB() {
            binding.constraint1.visibility = View.VISIBLE
            binding.constraint2.visibility = View.GONE
        }

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
