package com.shenawynkov.criticaltest.ui.article


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shenawynkov.criticaltest.data.BaseRepo
import com.shenawynkov.criticaltest.data.HomeRepo
import com.shenawynkov.criticaltest.ui.HomeViewModel

import javax.inject.Inject

class ArticleViewModelFactory @Inject constructor(

    private val repo: BaseRepo

) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ArticleViewModel(

            repo
        ) as T
    }


}