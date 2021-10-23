package com.shenawynkov.criticaltest.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shenawynkov.criticaltest.data.HomeRepo

import javax.inject.Inject

class HomeViewModelFactory @Inject constructor(

    private val repo: HomeRepo

) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(

            repo
        ) as T
    }


}