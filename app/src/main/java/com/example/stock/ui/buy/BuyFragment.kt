package com.example.stock.ui.buy

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stock.R
import com.example.stock.data.MStock
import com.example.stock.data.Stock
import com.example.stock.ui.ViewModelFactory
import com.example.stock.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_buy.*
import kotlinx.android.synthetic.main.fragment_buy.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class BuyFragment : Fragment() ,BuyAdapter.BuyItemClickListener{

    private lateinit var buyViewModel: BuyViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        buyViewModel =
                ViewModelProvider(this, ViewModelFactory.creatFactory(activity as MainActivity))
                    .get(BuyViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_buy, container, false)
        val _adapter=BuyAdapter()
        val btnTime=root.btnTime
        val time = System.currentTimeMillis()
        val calender=Calendar.getInstance()
        val dateformat = SimpleDateFormat("yyyyMMdd")
        val date=dateformat.format(time)
        btnTime.setText(date)
        buyViewModel.date.value = date
        _adapter.listener=this
        root.recycle_buy.apply {
            setHasFixedSize(true)
            layoutManager=LinearLayoutManager(this@BuyFragment.context)
            adapter=_adapter
        }
//        buyViewModel.getAll().observe(viewLifecycleOwner, Observer {
//            _adapter.submitList(it)
//        })
        buyViewModel.getStockByDate().observe(viewLifecycleOwner, Observer {
            if(it.isEmpty()){
                fillWithStartingData(dateformat.format(calender.time))
            }
            _adapter.submitList(it)

        })
        btnTime.setOnClickListener {

            DatePickerDialog(
                activity as MainActivity,
                { datePicker, year, month, day ->
                    calender.set(year, month, day)
                    val _date = dateformat.format(calender.time)
                    btnTime.setText(_date)
                    Log.d("TAG", "onCreateView: ${_date}")
                    buyViewModel.date.value = _date

                },
                calender.get(Calendar.YEAR),
                calender.get(Calendar.MONTH),
                calender.get(Calendar.DAY_OF_MONTH)
            )
                .show()
        }
        return root
    }

    override fun itemClick(stock: Stock, position: Int) {
        AlertDialog.Builder(this.context).setMessage("是否購買${stock.name}，現價${stock.close}")
            .setPositiveButton("OK") { _, _ ->
                buyViewModel.insertMyself(MStock(0,name = stock.name
                    ,date = stock.date,code = stock.code,close = stock.close))
            }.setNegativeButton("Cancal",null)
            .show()
    }
    @WorkerThread
    private fun fillWithStartingData(date: String) {
//        val date2 = dateformat.format(time- (86400000*2))
//        val date3 = dateformat.format(time- (86400000*3))
//        val date4 = dateformat.format(time- (86400000*4))
        val dates= listOf(date)
        var position=0
        val urls= listOf("https://www.twse.com.tw/exchangeReport/MI_INDEX?response=json&type=ALLBUT0999&date=$date")
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

                            when (state) {
                                "OK"-> {
                                    val moreData = obj.getJSONArray("data9")
                                    val _position = position++
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

                                        buyViewModel.insert(
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
                                                dealNumber
                                            )
                                        )
                                    }
                                }
                                "很抱歉，沒有符合條件的資料!","查詢日期大於今日，請重新查詢!"->{
                                    buyViewModel.insert(
                                        Stock(0,name = state,date = date)
                                    )
                                }
                                else->{
                                    Toast.makeText(activity as MainActivity,"讀取中"
                                        ,Toast.LENGTH_SHORT)
                                        .show()
                                }

                            }

                        }

                    }

                })

            }
        }

    }
}