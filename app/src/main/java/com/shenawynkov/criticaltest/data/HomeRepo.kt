package com.shenawynkov.criticaltest.data

import ae.digitalwise.jadeer.networking.models.safeCall.ResultWrapper
import ae.digitalwise.jadeer.networking.models.safeCall.handleRegisterError
import ae.digitalwise.jadeer.networking.models.safeCall.safeApiCall
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.shenawynkov.criticaltest.BuildConfig
import com.shenawynkov.criticaltest.data.home.Article
import com.shenawynkov.criticaltest.networking.ApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class HomeRepo(val apiService: ApiService) : BaseRepo(apiService) {
    val pageSize = 10


    suspend fun getHeadlines(
        source:String,
        page:Int,
        dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) :  List<Article>{
        val response = safeApiCall(dispatcher) {
            apiService.get_Headlines(
                source, BuildConfig.api_key,page,pageSize

            )
        }
        handleRegisterError(response, message, status)
        if (response is ResultWrapper.Success) {
            status.value = response.value.status

           return response.value.articles

        }

        return ArrayList()
    }

}