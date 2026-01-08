package com.nestpay.pg.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.nestpay.pg.databinding.ItemPayListBinding
import com.nestpay.pg.domain.model.local.Order
import com.nestpay.pg.domain.model.remote.Github
import com.nestpay.pg.presentation.base.BaseAdapter
import com.nestpay.pg.presentation.base.BaseHolder

class PayListAdapter : BaseAdapter<Order, ItemPayListBinding, PayListHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayListHolder = PayListHolder(
        ItemPayListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
}

class PayListHolder(
    viewBinding: ItemPayListBinding,
) : BaseHolder<Order, ItemPayListBinding>(viewBinding) {

    /*
    * 데이터를 받아올 때 사용
    * */
    override fun bind(binding: ItemPayListBinding, item: Order?) {

        item?.let { data ->
            binding.apply {

                textPayDateValue.text = data.payDay
                textProductNameValue.text = data.productName
                textProductPriceValue.text = data.productPrice
                textCardTypeValue.text = data.idx.toString()
                textCardNumberValue.text = data.idx.toString()
            }
        } ?: return
    }

    /*override fun bind(binding: ItemPayListBinding, item: Order?) {

        item?.let { data ->
            binding.apply {

                textPayDateValue.text = data.payDay.toString()
                textProductNameValue.text = data.productName
                textProductPriceValue.text = data.productPrice
                textCardTypeValue.text = data.cardType
                textCardNumberValue.text = data.cardNum
            }
        } ?: return
    }*/
}



