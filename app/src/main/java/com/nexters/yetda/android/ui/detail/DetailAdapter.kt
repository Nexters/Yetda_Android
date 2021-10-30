package com.nexters.yetda.android.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.islamkhsh.CardSliderAdapter
import com.nexters.yetda.android.R
import com.nexters.yetda.android.databinding.ItemDetailListBinding
import com.nexters.yetda.android.domain.database.model.Present

class DetailAdapter(private val items: ArrayList<Present>) :
    CardSliderAdapter<DetailAdapter.ViewHolder>() {

    override fun getItemCount() = items.size


    override fun bindVH(holder: DetailAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.apply {
            bind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            DetailAdapter.ViewHolder {
        val binding = ItemDetailListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ItemDetailListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Present) {
            binding.tvItemDetailName.text = item.name
            Glide
                .with(itemView.context)
                .load(item.image)
                .fitCenter()
                .placeholder(R.drawable.img_present_default)
                .into(binding.ivItemDetail)
        }
    }

}