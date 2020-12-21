package com.example.stock.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = StockName.COL_TABLE)
data class Stock(
    @PrimaryKey(autoGenerate = true)
    val id :Long,
    @ColumnInfo(name = StockName.COL_CODE)
    val code:String="",
    @ColumnInfo(name = StockName.COL_NAME)
    val name:String="",
    @ColumnInfo(name = StockName.COL_DATE)
    val date:String="",
    @ColumnInfo(name = StockName.COL_TRADING_VOLUME)
    val tradingVolume:String="",
    @ColumnInfo(name = StockName.COL_TRADING_VALUE)
    val tradingValue:String="",
    @ColumnInfo(name = StockName.COL_EX_DISTRIBUTION)
    val exDistribution:String="",
    @ColumnInfo(name = StockName.COL_MAXVALUE)
    val maxValue:String="",
    @ColumnInfo(name = StockName.COL_MINVALUE)
    val minValue:String="",
    @ColumnInfo(name = StockName.COL_CLOSE)
    val close:String="",
    @ColumnInfo(name = StockName.COL_DIFF)
    val diff:String="",
    @ColumnInfo(name = StockName.COL_DEALNUMBER)
    val dealNumber:String="")