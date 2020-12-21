package com.example.stock.ui.buy

import androidx.recyclerview.widget.DiffUtil
import com.example.stock.data.Stock

class BDiffCallback :DiffUtil.ItemCallback<Stock>() {
    override fun areItemsTheSame(oldItem: Stock, newItem: Stock): Boolean {
        return oldItem.id==newItem.id
    }

    override fun areContentsTheSame(oldItem: Stock, newItem: Stock): Boolean {
        return  oldItem.id==newItem.id&&
                oldItem.name==newItem.name
    }
}