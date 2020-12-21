package com.example.stock.ui.buy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.stock.data.DataRepository
import com.example.stock.data.MStock
import com.example.stock.data.Stock

class BuyViewModel(private val repository: DataRepository) : ViewModel() {
    val date=MutableLiveData<String>()

    fun getAll():LiveData<PagedList<Stock>>{
        return repository.getAll()
    }
//    fun update(stockName:String,date:String){
//        repository.updateBuy(stockName,date)
//    }
    fun insertMyself(stock: MStock){
        repository.insertMyself(stock)
    }
    fun getStockByDate():LiveData<PagedList<Stock>>{

        return Transformations.switchMap(date){
            repository.getStocksByDate(date.value!!)
        }
    }
    fun insert(stock: Stock){
        repository.insert(stock)
    }


}