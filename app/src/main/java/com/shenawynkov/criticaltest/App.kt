package com.shenawynkov.criticaltest




import android.app.Application

import com.shenawynkov.criticaltest.di.ApplicationComponent
import com.shenawynkov.criticaltest.di.DaggerApplicationComponent
import com.shenawynkov.criticaltest.di.RepoModule
import java.util.*


class App : Application() {


    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.builder().repoModule(RepoModule(this)).build()
        appComponent.inject(this)


    }


}