package com.nexters.yetda.android.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.nexters.yetda.android.R
import com.nexters.yetda.android.database.model.History
import io.realm.RealmResults
import kotlinx.android.synthetic.main.item_present_list.view.*

class HomeAdapter(private val items: ArrayList<History>) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        //click시 토스트
        val item = items[position]
        val listener = View.OnClickListener {it ->
            Toast.makeText(it.context, "Clicked: ${item.name}", Toast.LENGTH_SHORT).show()
        }
        holder.apply {
            bind(listener, item)
            itemView.tag = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            HomeAdapter.ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_present_list, parent, false)
        return HomeAdapter.ViewHolder(inflatedView)
    }


    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view:View = v
        fun bind(listener: View.OnClickListener, item: History) {
            view.tv_item_name.text = item.name
            view.tv_item_birthday.text = item.birthday
            view.tv_item_price.text = item.startPrice.toString() + "~" + item.endPrice.toString()
            view.tv_item_tag1.text = item.presents.get(0)!!.name
            view.tv_item_tag2.text = item.presents.get(1)!!.name
            view.setOnClickListener(listener)
        }
    }
}