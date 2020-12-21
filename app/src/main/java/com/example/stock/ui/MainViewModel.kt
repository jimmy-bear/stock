package com.example.stock.ui

import androidx.lifecycle.ViewModel
import com.example.stock.data.DataRepository
import com.example.stock.data.Stock
import com.example.stock.data.MStock

class MainViewModel (val repository: DataRepository):ViewModel() {

    fun insertBuy(stock: Stock){
        repository.insert(stock)
    }
    fun insertMysell(stock: MStock){
        repository.insertMyself(stock)
    }


}