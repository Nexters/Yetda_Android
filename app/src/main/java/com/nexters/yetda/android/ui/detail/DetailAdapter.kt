package com.nexters.yetda.android.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.islamkhsh.CardSliderAdapter
import com.nexters.yetda.android.R
import com.nexters.yetda.android.database.model.Present
import kotlinx.android.synthetic.main.item_detail_list.view.*
import kotlin.collections.ArrayList

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
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_detail_list, parent, false)
        return DetailAdapter.ViewHolder(inflatedView)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        fun bind(item: Present) {
            view.tv_item_detail_name.text = item.name
            Glide
                .with(view.context)
                .load(item.image)
                .fitCenter()
                .placeholder(R.drawable.img_present_default)
                .into(view.iv_item_detail)
        }
    }

}