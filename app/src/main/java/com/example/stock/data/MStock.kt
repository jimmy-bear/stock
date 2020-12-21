package com.example.stock.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = StockName.COL_M_TABLE)
data class MStock(
    @PrimaryKey(autoGenerate = true)
    val id :Long,
    @ColumnInfo(name = StockName.COL_CODE)
    val code:String="",
    @ColumnInfo(name = StockName.COL_NAME)
    val name:String="",
    @ColumnInfo(name = StockName.COL_DATE)
    val date:String="",
    @ColumnInfo(name = StockName.COL_CLOSE)
    val close:String="",
    @ColumnInfo(name = StockName.COL_DIFF)
    val diff:String="",
    @ColumnInfo(name = StockName.COL_DEALNUMBER)
    val dealNumber:String=""
    )
