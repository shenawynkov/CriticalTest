package com.shenawynkov.criticaltest.data

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.shenawynkov.criticaltest.networking.ApiService


open class BaseRepo(
    val apiClientBase: ApiService,

) {

    lateinit var status: MutableLiveData<String>
    lateinit var message: MutableLiveData<String>

    init {
        status = MutableLiveData()
        message = MutableLiveData()
    }






}