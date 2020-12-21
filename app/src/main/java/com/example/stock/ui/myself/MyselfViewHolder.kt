package com.example.stock.ui.myself

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_myself_stock.view.*

class MyselfViewHolder(view:View):RecyclerView.ViewHolder(view) {
    val id=view.txIdMyself
    val name=view.txNameMyself
    val buyPrice=view.txBuyPriceMyself
    val nowPrice=view.txNowPriceMyself
    val diff=view.txdiffMyself
    val note=view.txNoteMyself
}