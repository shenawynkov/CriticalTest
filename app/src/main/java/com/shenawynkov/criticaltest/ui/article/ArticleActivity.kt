package com.shenawynkov.criticaltest.ui.article

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.shenawynkov.criticaltest.App
import com.shenawynkov.criticaltest.R
import com.shenawynkov.criticaltest.base.BaseActivity
import com.shenawynkov.criticaltest.data.home.Article
import com.shenawynkov.criticaltest.databinding.ActivityArticleBinding
import com.shenawynkov.criticaltest.ui.HomeViewModelFactory
import javax.inject.Inject

class ArticleActivity : BaseActivity<ArticleViewModel,ActivityArticleBinding>() {
    @Inject
    lateinit var factory: ArticleViewModelFactory
    companion object{
        const val ARTICLE="ARTICLE"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as App).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding.viewmodel=viewModel
       viewModel.article.value= intent.getParcelableExtra<Article>(ARTICLE)
    }

    override fun createBinding() =R.layout.activity_article
    override fun provideViewModel() =ViewModelProvider(this,factory).get(ArticleViewModel::class.java)
}