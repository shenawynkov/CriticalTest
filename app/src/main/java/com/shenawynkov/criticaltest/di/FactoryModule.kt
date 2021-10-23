package com.shenawynkov.criticaltest.di



import com.shenawynkov.criticaltest.data.BaseRepo
import com.shenawynkov.criticaltest.data.HomeRepo
import com.shenawynkov.criticaltest.ui.HomeViewModelFactory
import com.shenawynkov.criticaltest.ui.article.ArticleViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FactoryModule {

    @Provides
    @Singleton
    fun homeFactory(

            repo: HomeRepo
    ): HomeViewModelFactory {
        return HomeViewModelFactory(

                repo
        )
    }
    @Provides
    @Singleton
    fun ArticleFactory(

            repo: BaseRepo
    ): ArticleViewModelFactory {
        return ArticleViewModelFactory(

                repo
        )
    }


}