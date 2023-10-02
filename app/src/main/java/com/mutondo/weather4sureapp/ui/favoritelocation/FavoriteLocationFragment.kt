package com.mutondo.weather4sureapp.ui.favoritelocation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mutondo.weather4sureapp.databinding.FragmentFavoriteLocationBinding
import com.mutondo.weather4sureapp.domain.models.FavoriteLocation
import com.mutondo.weather4sureapp.ui.BaseFragment
import com.mutondo.weather4sureapp.ui.FavoriteLocationViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteLocationFragment :BaseFragment() {

    private var binding: FragmentFavoriteLocationBinding? = null

    @Inject
    lateinit var viewModel: FavoriteLocationViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteLocationBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showActionBar()
        setActionBarTitle("Favorite Locations")
        observeFavoriteLocations()
    }

    private fun observeFavoriteLocations() {
        viewModel.getFavoriteLocations().observe(viewLifecycleOwner) {
            setupRecyclerView(it)
        }
    }

    private fun setupRecyclerView(locations: List<FavoriteLocation>) {

        with(binding!!) {
            val layoutManager = LinearLayoutManager(context)

            recyclerview.layoutManager = LinearLayoutManager(context)
            recyclerview.hasFixedSize()
            recyclerview.adapter = FavoriteLocationAdapter(locations)
            recyclerview.addItemDecoration(DividerItemDecoration(requireContext(), layoutManager.orientation))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        const val TAG = "FavoriteLocationFragment"
    }
}