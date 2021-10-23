
package com.shenawynkov.criticaltest.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import observeIfNotNull
import kotlin.coroutines.CoroutineContext


abstract class BaseFragment<T : BaseViewModel, G : ViewDataBinding> : Fragment(),
    CoroutineScope {
    private lateinit var job: Job
    lateinit var binding: G
    lateinit var viewModel: T
    private var connectionDialog: AlertDialog? = null
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    lateinit var statusObserver: Observer<Int>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind(createBinding(), container)

        return binding.root
    }

    override fun onDestroy() {
        if(this::job.isInitialized)
        job.cancel()

        connectionDialog?.dismiss()
        super.onDestroy()
    }

    abstract fun provideViewModel(): T

    abstract fun createBinding(): Int

    private fun bind(layout: Int, container: ViewGroup?) {
        binding = DataBindingUtil.inflate<G>(
            layoutInflater, layout, container, false
        )



        binding.lifecycleOwner = this


    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        job = Job()
        viewModel = provideViewModel()
        setup()
       // observeErrors()
        observeOnBackPressed()


    }


    abstract fun setup()
    fun observeOnBackPressed() {
        viewModel.backPressed.observeIfNotNull(this, Observer {
            if (it == true)
                activity?.supportFragmentManager?.popBackStack()

        })
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

    open fun setupUI(view: View) { // Set up touch listener for non-text box views to hide keyboard.



    }
}