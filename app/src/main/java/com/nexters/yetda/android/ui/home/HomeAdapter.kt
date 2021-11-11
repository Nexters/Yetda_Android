package com.nexters.yetda.android.ui.home

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.nexters.yetda.android.databinding.ItemPresentListBinding
import com.nexters.yetda.android.domain.database.model.History
import java.text.NumberFormat
import java.time.MonthDay
import java.time.format.DateTimeFormatter
import java.util.*

class HomeAdapter(private val items: ArrayList<History>, HistoryLisner: HistoryLisner) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        //click시 토스트
        val item = items[position]
        holder.apply {
            bind(item)
            //todo: tag를 왜 사용했는지 확인 필요, 리스너 때문이었나 ??
            itemView.tag = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPresentListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ItemPresentListBinding, HistoryLisner: HistoryLisner) : RecyclerView.ViewHolder(binding.root) {
        private val TAG = javaClass.simpleName

        //todo: 코드정리
        fun bind(item: History) {
            binding.tvItemName.text = item.name

            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (item.birthday.length == 4) {
                        val newFormatter = DateTimeFormatter.ofPattern("M월 d일", Locale.ENGLISH)
                        val date = MonthDay.of(
                            item.birthday.substring(0, 2).toInt(),
                            item.birthday.substring(2, 4).toInt()
                        )
                        binding.tvItemBirthday.text = date.format(newFormatter)
                    } else {
                        binding.tvItemBirthday.text = item.birthday
                    }
                } else {
                    if (item.birthday.length == 4) {
                        binding.tvItemBirthday.text =
                            "${item.birthday.substring(0, 2)}월 ${item.birthday.substring(2, 4)}일"
                    } else {
                        binding.tvItemBirthday.text = item.birthday
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "* * * e ::: ${e.message}")
                if (item.birthday.length == 4) {
                    binding.tvItemBirthday.text =
                        "${item.birthday.substring(0, 2)}월 ${item.birthday.substring(2, 4)}일"
                } else {
                    binding.tvItemBirthday.text = item.birthday
                }
            }


            binding.tvItemPrice.text =
                "${NumberFormat.getCurrencyInstance(Locale.KOREA).format(item.startPrice)} ~ ${
                    NumberFormat.getCurrencyInstance(
                        Locale.KOREA
                    ).format(item.endPrice)
                }"

            if (item.presents.size > 0) {
                binding.tvItemTag1.text = item.presents.get(0)!!.name
                if (item.presents.size > 1) {
                    binding.tvItemTag2.text = item.presents.get(1)!!.name
                } else {
                    binding.tvItemTag2.visibility = View.GONE
                }
            } else {
                binding.tvItemTag1.visibility = View.GONE
                binding.tvItemTag2.visibility = View.GONE
            }

            binding.delButton.setOnClickListener {
              notifyItemRemoved(item.id) //이것도 왜 안됑,, 옵저버 개념?
                HistoryLisner.onDelClick(item.id) //-> 왜 오류뜨나유..
                //삭제
                Toast.makeText(it.context, "hi", Toast.LENGTH_SHORT).show()
            }

            itemView.setOnClickListener {
                itemView.findNavController()
                    .navigate(HomeFragmentDirections.actionHomeToDetail(item))
//            Toast.makeText(it.context, "Clicked: ${item.name}", Toast.LENGTH_SHORT).show()
            }

//            binding.setOnClickListener(listener)
        }
    }


}