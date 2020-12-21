package com.example.stock.ui.buy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import com.example.stock.R
import com.example.stock.data.Stock

class BuyAdapter :PagedListAdapter<Stock,BuyViewHolder>(BDiffCallback()) {
    interface BuyItemClickListener{
        fun itemClick(stock: Stock, position: Int)
    }
    var listener:BuyItemClickListener? =null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyViewHolder {
        val view=LayoutInflater.from(parent.context)
            .inflate(R.layout.item_buy_stock,parent,false)
        return BuyViewHolder(view)
    }

    override fun onBindViewHolder(holder: BuyViewHolder, position: Int) {
        getItem(position)?.also {stock->
            holder.id.setText(stock.code)
            holder.name.setText(stock.name)
            holder.price.setText(stock.close)
            holder.reason.setText(stock.diff)
            holder.itemView.setOnClickListener {
                listener?.run {
                    itemClick(stock,position)
                }
            }
        }

    }

}