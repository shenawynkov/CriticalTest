package com.shenawynkov.criticaltest.ui

import android.annotation.SuppressLint
import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricPrompt
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.shenawynkov.criticaltest.App
import com.shenawynkov.criticaltest.R
import com.shenawynkov.criticaltest.base.BaseActivity
import com.shenawynkov.criticaltest.base.GenericAdapter
import com.shenawynkov.criticaltest.data.home.Article
import com.shenawynkov.criticaltest.databinding.ActivityHomeBinding
import com.shenawynkov.criticaltest.ui.article.ArticleActivity
import com.shenawynkov.criticaltest.utils.EndlessRecyclerViewScrollListener
import observeIfNotNull
import java.util.concurrent.Executor
import javax.inject.Inject

class HomeActivity : BaseActivity<HomeViewModel, ActivityHomeBinding>() {
    @Inject
    lateinit var factory: HomeViewModelFactory
    lateinit var articleAdapter: GenericAdapter

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as App).appComponent.inject(this)


        super.onCreate(savedInstanceState)
        //auth
        val biometricManager = BiometricManager.from(this)
        if (biometricManager.canAuthenticate(BIOMETRIC_WEAK) == BiometricManager.BIOMETRIC_SUCCESS&&!viewModel.auth) {
            authenticate()
            viewModel.auth=true
        }

        //viewmodel
        viewModel.source.value=getString(R.string.source)
        binding.viewmodel = viewModel
        //init
        observeArticles()
        initArticleAdapter()
        viewModel.loadArticles(1)

    }

    private fun initArticleAdapter() {
        binding.rvArticles.apply {
            articleAdapter =
                GenericAdapter(::moveToArticle, ArrayList<Article>(), R.layout.item_articles)
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(this@HomeActivity)
            addOnScrollListener(object :
                EndlessRecyclerViewScrollListener(layoutManager as LinearLayoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int) {
                    viewModel.loadArticles(page)
                }

            })


        }
    }

    fun moveToArticle(item: Any) {
        item as Article

        val intent = Intent(this, ArticleActivity::class.java)
        intent.putExtra(ArticleActivity.ARTICLE, item)
        startActivity(intent)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun observeArticles() {
        viewModel.articles.observeIfNotNull(this, Observer {
            articleAdapter.list = it
            articleAdapter.notifyDataSetChanged()
        })

    }

    override fun createBinding(): Int {
        return R.layout.activity_home
    }

    override fun provideViewModel(): HomeViewModel {
        return ViewModelProvider(this, factory).get(HomeViewModel::class.java)

    }

    fun authenticate() {
        executor = ContextCompat.getMainExecutor(this)

        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        applicationContext,
                        "Authentication error: $errString", Toast.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(
                        applicationContext,
                        "Authentication succeeded!", Toast.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        applicationContext, "Authentication failed",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("login for my app")
            .setSubtitle("Log in using your fingerprint")
            .setNegativeButtonText("Cancel")
            .build()




        biometricPrompt.authenticate(promptInfo)

    }


}