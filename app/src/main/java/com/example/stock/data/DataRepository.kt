package com.example.stock.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.sqlite.db.SimpleSQLiteQuery
import java.util.concurrent.Executors

class DataRepository private constructor(private val dao: StockDao){

    fun insert(stock: Stock){
        Executors.newSingleThreadExecutor().execute {
            dao.insert(stock)
        }
    }
    fun insertMyself(stock: MStock){
        Executors.newSingleThreadExecutor().execute {
            dao.insertMyself(stock)
        }
    }
//    fun updateBuy(stockName:String,date: String){
//        Executors.newSingleThreadExecutor().execute {
//            dao.updateBuy(stockName,date)
//        }
//
//    }
    fun getAll() :LiveData<PagedList<Stock>>{
        val query=StringBuilder().apply {
            append("select * from stock")
        }

        val config=PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(LOAD_SIZE)
            .build()

        return LivePagedListBuilder<Int, Stock>(
            dao.getAll(SimpleSQLiteQuery(query.toString())),config).build()
    }
//    fun getMyself():LiveData<PagedList<Stock>>{
//        val query=StringBuilder().apply {
//            append("select * from stock where isHave = 1")
//        }
//
//        val config=PagedList.Config.Builder()
//            .setPageSize(PAGE_SIZE)
//            .setEnablePlaceholders(true)
//            .setInitialLoadSizeHint(LOAD_SIZE)
//            .build()
//
//        return LivePagedListBuilder<Int, Stock>(
//            dao.getMyself(SimpleSQLiteQuery(query.toString())),config).build()
//    }
    fun getStocksByDate(date:String):LiveData<PagedList<Stock>>{
        val query=java.lang.StringBuilder().append("select *from stock where date =${date}")
        val config=PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(LOAD_SIZE)
            .setPrefetchDistance(50)
            .build()
        return LivePagedListBuilder<Int, Stock>(
            dao.getStocksByDate(SimpleSQLiteQuery(query.toString())),config).build()
    }
    fun getAllMyself():LiveData<PagedList<MStock>>{
        val query=java.lang.StringBuilder().append("select *from mStock")
        val config=PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(LOAD_SIZE)
            .setPrefetchDistance(50)
            .build()
        return LivePagedListBuilder<Int, MStock>(
            dao.getAllMyself(SimpleSQLiteQuery(query.toString())),config).build()
    }




    companion object{
        private var instence: DataRepository?=null
        val PAGE_SIZE=10
        val LOAD_SIZE=20
        fun getInstence(context: Context): DataRepository {
            if (instence ==null){
                val dao= StockDatabase.getInstence(context).stockDao()
                instence = DataRepository(dao)
            }
            return instence!!
        }
    }
}