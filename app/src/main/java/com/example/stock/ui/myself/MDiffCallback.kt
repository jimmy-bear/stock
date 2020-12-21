package com.example.stock.ui.myself

import androidx.recyclerview.widget.DiffUtil
import com.example.stock.data.MStock

class MDiffCallback:DiffUtil.ItemCallback<MStock>(){
    override fun areItemsTheSame(oldItem: MStock, newItem: MStock): Boolean {
        return oldItem.id==newItem.id
    }

    override fun areContentsTheSame(oldItem: MStock, newItem: MStock): Boolean {
        return  oldItem.id==newItem.id&&
                oldItem.name==newItem.name
    }
}