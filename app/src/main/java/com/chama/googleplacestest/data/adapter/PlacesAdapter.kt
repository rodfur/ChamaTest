package com.chama.googleplacestest.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chama.googleplacestest.R
import com.chama.googleplacestest.data.model.PlaceDTO
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_place.view.*

class PlacesAdapter(
    private val results: List<PlaceDTO>
) : RecyclerView.Adapter<PlacesAdapter.PlacesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, view: Int): PlacesViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_place, parent, false)
        return PlacesViewHolder(
            itemView
        )
    }

    override fun getItemCount() = results.count()

    override fun onBindViewHolder(viewHolder: PlacesViewHolder, position: Int) {
        viewHolder.bindView(results[position])
    }

    class PlacesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(placeDTO: PlaceDTO) {
            itemView.tv_name.text = placeDTO.name
            itemView.tv_vicinity.text = placeDTO.vicinity

            placeDTO.photoUrl?.let {
                Picasso.with(itemView.context)
                    .load(it)
                    .into(itemView.iv_photo)

                placeDTO.photoWidth?.run {
                    itemView.iv_photo.layoutParams.width = this
                }
                placeDTO.photoHeight?.run {
                    itemView.iv_photo.layoutParams.height = this
                }
            }
        }
    }
}