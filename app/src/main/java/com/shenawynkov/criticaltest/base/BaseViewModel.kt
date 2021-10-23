package com.shenawynkov.criticaltest.base

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shenawynkov.criticaltest.data.BaseRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(val repo: BaseRepo) : ViewModel(), CoroutineScope {
    val loadingStatus = MutableLiveData<Boolean>(false)
    val status = repo.status
    val message = repo.message
    var savedFun: () -> Unit = {}
    val title = MutableLiveData<String>()
    val backPressed = MutableLiveData<Boolean>(false)
    fun backClicked(view: View) {
        backPressed.value = true
    }


    init {
        status.value = null
        message.value = null
    }
    val job = Job()

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    fun retry(view: View?) {
        savedFun()
    }


}