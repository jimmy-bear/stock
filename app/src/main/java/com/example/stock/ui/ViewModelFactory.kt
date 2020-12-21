package com.example.stock.ui

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stock.data.DataRepository
import java.lang.IllegalStateException

class ViewModelFactory private constructor(
    private val repository: DataRepository
):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(DataRepository::class.java)
            .newInstance(repository)
    }

    companion object{
        fun creatFactory(activity: Activity): ViewModelFactory {
            val context = activity.applicationContext ?: throw IllegalStateException("No application")
            return ViewModelFactory(DataRepository.getInstence(context))
        }
    }
}