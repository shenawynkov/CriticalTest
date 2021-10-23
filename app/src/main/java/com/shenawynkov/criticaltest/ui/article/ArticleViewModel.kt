package com.shenawynkov.criticaltest.ui.article

import androidx.lifecycle.MutableLiveData
import com.shenawynkov.criticaltest.base.BaseViewModel
import com.shenawynkov.criticaltest.data.BaseRepo
import com.shenawynkov.criticaltest.data.HomeRepo
import com.shenawynkov.criticaltest.data.home.Article
import kotlinx.coroutines.launch

class ArticleViewModel (val baseRepo: BaseRepo) :BaseViewModel(baseRepo){
    val article=MutableLiveData<Article>()

}