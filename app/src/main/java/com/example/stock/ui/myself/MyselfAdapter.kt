package com.example.stock.ui.myself

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import com.example.stock.R
import com.example.stock.data.MStock

class MyselfAdapter:PagedListAdapter<MStock,MyselfViewHolder>(MDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyselfViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_myself_stock,parent,false)
            return MyselfViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyselfViewHolder, position: Int) {
        val item=getItem(position)!!
        holder.id.setText(item.code)
        holder.name.setText(item.name)
        holder.nowPrice.setText(item.close)
        //holder.buyPrice
        holder.note.setText(item.diff)

    }
}