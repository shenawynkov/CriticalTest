package com.shenawynkov.criticaltest.base


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer

import com.shenawynkov.errorhandler.Errors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import observeIfNotNull
import java.util.*
import kotlin.coroutines.CoroutineContext


abstract class BaseActivity<T : BaseViewModel, G : ViewDataBinding> : AppCompatActivity(),

    CoroutineScope {



    private lateinit var job: Job
    lateinit var viewModel: T
    lateinit var binding: G
    lateinit var statusObserver: Observer<String>


    abstract fun createBinding(): Int
    open fun inject() {}
    private fun bind(layout: Int) {
        binding = DataBindingUtil.setContentView<G>(
            this,
            layout
        )

        //    binding.root.setLayoutDirection(TextUtils.getLayoutDirectionFromLocale(locale))

        binding.lifecycleOwner = this

    }

    abstract fun provideViewModel(): T

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        inject()
        job = Job()
        bind(createBinding())

        viewModel = provideViewModel()
        observeOnBackPressed()
        observeErrors()




    }
    override fun onResume() {
        super.onResume()



    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()

    }



    fun observeOnBackPressed() {
        viewModel.backPressed.observeIfNotNull(this, androidx.lifecycle.Observer {
            if (it == true)
                onBackPressed()
        })
    }

    open fun backPressed() {
        finish()

    }

 fun observeErrors() {
     var dialog = false
     Log.d("observeError", "observed" + this.javaClass.canonicalName)
     Log.d("observeError", "observed")
     statusObserver = Observer {
         Log.d("ResponseCodeError", it.toString())
         when (it) {


         }
         viewModel.status.observeIfNotNull(this, statusObserver)
     }


 }
    open fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager: InputMethodManager = activity.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager

        activity.currentFocus?.let {
            inputMethodManager.hideSoftInputFromWindow(
                it.windowToken, 0
            )
        }
    }


}
