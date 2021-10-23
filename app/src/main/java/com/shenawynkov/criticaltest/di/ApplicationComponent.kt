
package com.shenawynkov.criticaltest.di






import ae.digitalwise.ecommerce.RepoModule
import com.shenawynkov.criticaltest.App
import com.shenawynkov.criticaltest.ui.HomeActivity
import com.shenawynkov.criticaltest.ui.article.ArticleActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, RepoModule::class, FactoryModule::class])
interface ApplicationComponent {

    fun  inject(app: App)

    fun inject(activity: HomeActivity)
    fun inject(activity: ArticleActivity)

}