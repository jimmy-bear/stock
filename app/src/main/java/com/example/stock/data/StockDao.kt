package com.example.stock.data

import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery

@Dao
interface StockDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(stock: Stock)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMyself(stock:MStock)
    @RawQuery(observedEntities = [Stock::class])
    fun getAll(query: SimpleSQLiteQuery) :DataSource.Factory<Int, Stock>
//    @RawQuery(observedEntities = [Stock::class])
//    fun getMyself(query: SimpleSQLiteQuery):DataSource.Factory<Int, Stock>
//    @Query("update stock set isHave= 1 where name=:stockName and date=:date" )
//    fun updateBuy(stockName: String,date:String)
    @RawQuery(observedEntities = [Stock::class])
    fun getStocksByDate(query: SimpleSQLiteQuery):DataSource.Factory<Int, Stock>
    @RawQuery(observedEntities = [MStock::class])
    fun getAllMyself(query: SimpleSQLiteQuery):DataSource.Factory<Int,MStock>
}