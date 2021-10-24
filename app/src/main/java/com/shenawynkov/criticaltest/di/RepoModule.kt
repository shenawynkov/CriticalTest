
package com.shenawynkov.criticaltest.di


import android.content.Context
import com.shenawynkov.criticaltest.data.BaseRepo
import com.shenawynkov.criticaltest.data.HomeRepo
import com.shenawynkov.criticaltest.networking.ApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RepoModule(val context: Context) {

    @Provides //scope is not necessary for parameters stored within the module
    fun context(): Context {
        return context
    }




    @Singleton
    @Provides
    fun provideHomeRepo(apiClient: ApiService): HomeRepo {
        return HomeRepo(apiClient)
    }
    @Singleton
    @Provides
    fun provideBaseRepo(apiClient: ApiService): BaseRepo {
        return BaseRepo(apiClient)
    }


}