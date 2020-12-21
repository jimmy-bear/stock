package com.example.stock.ui.myself

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.stock.data.DataRepository
import com.example.stock.data.MStock

class MySelfViewModel(val repository: DataRepository) : ViewModel() {
    fun getMyself():LiveData<PagedList<MStock>>{
        return repository.getAllMyself()
    }

}