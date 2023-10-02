package com.mutondo.weather4sureapp.ui.favoritelocation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mutondo.weather4sureapp.databinding.ItemFavoriteLocationBinding
import com.mutondo.weather4sureapp.domain.models.FavoriteLocation

class FavoriteLocationAdapter(
    private val locations: List<FavoriteLocation>
) : RecyclerView.Adapter<FavoriteLocationAdapter.FavoriteLocationViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteLocationViewHolder {
        return FavoriteLocationViewHolder(ItemFavoriteLocationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount(): Int = locations.size

    override fun onBindViewHolder(holder: FavoriteLocationViewHolder, position: Int) {
        holder.bind(locations[position])
    }

    inner class FavoriteLocationViewHolder(private val binding: ItemFavoriteLocationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(location: FavoriteLocation) {
            val loc = "${location.latitude}, ${location.longitude}"

            binding.name.text = location.name
            binding.location.text = loc
        }
    }
}