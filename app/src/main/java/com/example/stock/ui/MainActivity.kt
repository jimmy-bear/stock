package com.example.stock.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.stock.R
import com.example.stock.data.Stock
import com.example.stock.data.MStock
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_buy,R.id.navigation_myself))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        viewModel=ViewModelProvider(this, ViewModelFactory.creatFactory(this))
            .get(MainViewModel::class.java)
//        viewModel.insertBuy(Stock(0))
//        viewModel.insertMysell(MStock(0))


    //val client=OkHttpClient.Builder().build()
//        val calendar=Calendar.getInstance()
//        val year=calendar.get(Calendar.YEAR)
//        val month=calendar.get(Calendar.MONTH)
//        val day=calendar.get(Calendar.DAY_OF_MONTH)



    }
}