package com.example.stock.ui.buy

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_buy_stock.view.*

class BuyViewHolder(view:View):RecyclerView.ViewHolder(view) {
    val id=view.txIdBuy
    val name=view.txNameBuy
    val price=view.txPriceBuy
    val reason=view.txNoteBuy
}