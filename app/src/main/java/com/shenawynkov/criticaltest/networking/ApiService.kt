package com.shenawynkov.criticaltest.networking


import android.provider.ContactsContract
import com.shenawynkov.criticaltest.data.home.TopHeadlinesResponse
import com.shenawynkov.criticaltest.di.headlines
import retrofit2.http.*


interface ApiService {
    @GET(headlines)
    suspend fun get_Headlines(
        @Query("sources") sources: String,
        @Query("apiKey") key: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int

        ): TopHeadlinesResponse
}