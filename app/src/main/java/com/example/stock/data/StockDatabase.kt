package com.example.stock.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Stock::class, MStock::class],version = 1,exportSchema = false)
abstract class StockDatabase :RoomDatabase(){
    abstract fun stockDao(): StockDao
    companion object{

        private var instence : StockDatabase?=null

        fun getInstence(context: Context): StockDatabase {
            if(instence ==null) {
                instence = Room.databaseBuilder(
                    context,
                    StockDatabase::class.java,
                    "stocks.db")
                    .addCallback(StockAddCallback(context))
                    .build()
            }
        return instence!!

        }
    }
}