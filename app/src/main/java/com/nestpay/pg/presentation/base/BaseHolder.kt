package com.nestpay.pg.presentation.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseHolder<DataType, VB : ViewDataBinding> constructor(internal val viewDataBinding: VB) :
    RecyclerView.ViewHolder(viewDataBinding.root) {

    companion object {

        private val TAG = BaseHolder::class.java.simpleName
    }

    /**
     * Getter for [DataType] class
     */

    var item: DataType? = null

    /**
     * Binds holder data
     */
    abstract fun bind(binding: VB, item: DataType?)
    //abstract fun bind(binding: VB)
}