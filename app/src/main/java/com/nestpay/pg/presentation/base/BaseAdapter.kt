package com.nestpay.pg.presentation.base

import android.annotation.SuppressLint
import androidx.annotation.NonNull
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<ItemType, VB : ViewDataBinding, ViewHolderType : BaseHolder<ItemType, VB>> :
    RecyclerView.Adapter<ViewHolderType>() {

    companion object {

        private val TAG = BaseAdapter::class.java.simpleName
    }

    private val itemList: MutableList<ItemType> = ArrayList()
    private var itemPosition = -1

    override fun onBindViewHolder(@NonNull holder: ViewHolderType, @SuppressLint("RecyclerView") position: Int) {

        //holder.bind(holder.viewDataBinding)

        //데이터 받아올 때 사용
        val itemData = itemList[position] ?: return
        itemPosition = position
        holder.item = itemData
        holder.bind(holder.viewDataBinding, holder.item)
    }

    override fun getItemCount(): Int = itemList.size

    @SuppressLint("NotifyDataSetChanged")
    open fun replaceData(newList: List<ItemType>) {
        itemList.clear()
        itemList.addAll(newList ?: emptyList())
        notifyDataSetChanged()
    }

    fun getData(): MutableList<ItemType> {
        return itemList
    }
}