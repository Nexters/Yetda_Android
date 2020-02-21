package com.nexters.yetda.android.home

import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.nexters.yetda.android.R
import com.nexters.yetda.android.database.model.History
import com.nexters.yetda.android.detail.DetailActivity
import io.realm.RealmResults
import kotlinx.android.synthetic.main.item_present_list.view.*
import java.text.NumberFormat
import java.time.LocalDate
import java.time.MonthDay
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class HomeAdapter(private val items: ArrayList<History>) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        //click시 토스트
        val item = items[position]
        val listener = View.OnClickListener { it ->
            var intent = Intent(it.context, DetailActivity::class.java)
            intent.putExtra("ITEM", item)
            it.context.startActivity(intent)

//            Toast.makeText(it.context, "Clicked: ${item.name}", Toast.LENGTH_SHORT).show()
        }
        holder.apply {
            bind(listener, item)
            itemView.tag = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_present_list, parent, false)
        return ViewHolder(inflatedView)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val TAG = javaClass.simpleName
        private var view: View = v

        fun bind(listener: View.OnClickListener, item: History) {
            view.tv_item_name.text = item.name

            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (item.birthday.length == 4) {
                        val newFormatter = DateTimeFormatter.ofPattern("M월 d일", Locale.ENGLISH)
                        val date = MonthDay.of(item.birthday.substring(0, 2).toInt(), item.birthday.substring(2, 4).toInt())
                        view.tv_item_birthday.text = date.format(newFormatter)
                    } else {
                        view.tv_item_birthday.text = item.birthday
                    }
                } else {
                    if (item.birthday.length == 4) {
                        view.tv_item_birthday.text = "${item.birthday.substring(0, 2)}월 ${item.birthday.substring(2, 4)}일"
                    } else {
                        view.tv_item_birthday.text = item.birthday
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "* * * e ::: ${e.message}")
                if (item.birthday.length == 4) {
                    view.tv_item_birthday.text = "${item.birthday.substring(0, 2)}월 ${item.birthday.substring(2, 4)}일"
                } else {
                    view.tv_item_birthday.text = item.birthday
                }
            }


            view.tv_item_price.text =
                "${NumberFormat.getCurrencyInstance(Locale.KOREA).format(item.startPrice)} ~ ${NumberFormat.getCurrencyInstance(
                    Locale.KOREA
                ).format(item.endPrice)}"

            if (item.presents.size > 0) {
                view.tv_item_tag1.text = item.presents.get(0)!!.name
                if (item.presents.size > 1) {
                    view.tv_item_tag2.text = item.presents.get(1)!!.name
                } else {
                    view.tv_item_tag2.visibility = View.GONE
                }
            } else {
                view.tv_item_tag1.visibility = View.GONE
                view.tv_item_tag2.visibility = View.GONE
            }

            view.setOnClickListener(listener)
        }
    }


}