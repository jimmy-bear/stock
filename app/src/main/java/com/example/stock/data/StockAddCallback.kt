package com.example.stock.data

import android.content.Context
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.concurrent.Executors

class StockAddCallback(val context: Context) :RoomDatabase.Callback(){
    override fun onOpen(db: SupportSQLiteDatabase) {
        Log.d("TAG", "onOPEN:$")
    }

    override fun onCreate(db: SupportSQLiteDatabase) {
        Executors.newSingleThreadExecutor().execute {
            fillWithStartingData(context)
            Log.d("TAG", "onCreate: ")
        }

    }
    @WorkerThread
    private fun fillWithStartingData(context: Context) {
        val dao = StockDatabase.getInstence(context).stockDao()
        val time = System.currentTimeMillis()
        val dateformat = SimpleDateFormat("yyyyMMdd")
        val date1 = dateformat.format(time)
        val date2 = dateformat.format(time- 86400000)
        val date3 = dateformat.format(time- (86400000*2))
        val dates= listOf(date1,date2,date3)
        var position=0
        val urls= listOf("https://www.twse.com.tw/exchangeReport/MI_INDEX?response=json&type=ALLBUT0999&date=$date1",
                "https://www.twse.com.tw/exchangeReport/MI_INDEX?response=json&type=ALLBUT0999&date=$date2",
                "https://www.twse.com.tw/exchangeReport/MI_INDEX?response=json&type=ALLBUT0999&date=$date3")
        urls.forEach {
            val request1 = Request.Builder().url(it)
                    .build()
            CoroutineScope(Dispatchers.IO).launch {
                OkHttpClient().newCall(request1).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        Log.d("TAG", "onFailure: ")
                    }

                    override fun onResponse(call: Call, response: Response) {

                        response.body?.run {
                            val obj = JSONObject(string())
                            val state = obj.getString("stat")

                            if (state == "OK") {
                                val moreData = obj.getJSONArray("data9")
                                val _position=position++
                                Log.d("TAG", "onResponse: ${position}")
                                //"fields9":["0證券代號","1證券名稱","2成交股數","3成交筆數","4成交金額","5開盤價","6最高價","7最低價","8收盤價","9漲跌(+/-)"
                                for (z in 0 until moreData.length()) {

                                    val data = moreData.getJSONArray(z)
                                    val number = data.get(0).toString()
                                    val name = data.get(1).toString()
                                    val tradingVolume = data.get(2).toString()
                                    val dealNumber = data.get(3).toString()
                                    val tradingValue = data.get(4).toString()
                                    val exDistribution = data.get(5).toString()
                                    val maxValue = data.get(6).toString()
                                    val minValue = data.get(7).toString()
                                    val close = data.get(8).toString()
                                    val diff = data.get(9).toString()

                                    dao.insert(
                                            Stock(
                                                    0,
                                                    number,
                                                    name,
                                                    dates[_position],
                                                    tradingVolume,
                                                    tradingValue,
                                                    exDistribution,
                                                    maxValue,
                                                    minValue,
                                                    close,
                                                    diff,
                                                    dealNumber)
                                    )
                                }

                            }
                        }

                    }

                })

            }
        }

    }
}