package com.shenawynkov.criticaltest.ui

import androidx.lifecycle.MutableLiveData
import com.shenawynkov.criticaltest.base.BaseViewModel
import com.shenawynkov.criticaltest.data.HomeRepo
import com.shenawynkov.criticaltest.data.home.Article
import kotlinx.coroutines.launch

class HomeViewModel (val homeRepo: HomeRepo) :BaseViewModel(homeRepo){
    val articles=MutableLiveData<ArrayList<Article>>(ArrayList())
    val  source=MutableLiveData<String>("bbc-news")
    var auth=false


    fun loadArticles(page:Int)
    {
        launch {
            homeRepo.getHeadlines(source.value!!,articles,page)
        }
    }
}