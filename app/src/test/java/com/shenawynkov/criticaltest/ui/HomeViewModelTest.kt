package com.shenawynkov.criticaltest.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.shenawynkov.criticaltest.data.HomeRepo
import com.shenawynkov.criticaltest.data.home.Article
import com.shenawynkov.criticaltest.data.home.Source
import com.shenawynkov.criticaltest.data.home.TopHeadlinesResponse
import com.shenawynkov.criticaltest.networking.ApiService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {
    lateinit var SUT: HomeViewModel
    val homeRepo: HomeRepo = mockk()

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    private val testScope = TestCoroutineScope(testDispatcher)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        coEvery {
            homeRepo.status
        } returns MutableLiveData("success")
        coEvery {
            homeRepo.message
        } returns MutableLiveData("success")
        Dispatchers.setMain(testDispatcher)

        SUT = HomeViewModel(homeRepo)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun loadHeadliner_page1_DataReturned() = testScope.runBlockingTest {
        //Arrange
        val articles = mutableListOf<Article>()
        val article = Article(
            "bbc",
            "test_repo",
            "test_name",
            "test_desc",
            Source("BBC", "SS"),
            "s",
            "s",
            "en"
        )
        articles.add(article)
        coEvery {
            homeRepo.getHeadlines(
                SUT.source.value!!,

                1
            )
        } returns
                articles


        //Act
        SUT.loadArticles(1)
        //Assert
        coVerify { homeRepo.getHeadlines(SUT.source.value!!, 1) }
        assertEquals(articles, SUT.articles.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun loadHeadliner_page2_DataAdded() = testScope.runBlockingTest {
        //Arrange
        val articles = mutableListOf<Article>()
        val article1 = Article(
            "bbc",
            "test_repo",
            "test_name",
            "test_desc",
            Source("BBC", "SS"),
            "s",
            "s",
            "en"
        )
        val article2 = Article(
            "bbc",
            "test_repo2",
            "test_name2",
            "test_desc2",
            Source("BBC", "SS2"),
            "s2",
            "s2",
            "en"
        )
        articles.add(article1)
        SUT.articles.value?.add(article2)
        coEvery {
            homeRepo.getHeadlines(
                SUT.source.value!!,

                2
            )
        } returns
                articles


        //Act
        SUT.loadArticles(2)
        //Assert
        coVerify { homeRepo.getHeadlines(SUT.source.value!!, 2) }
        assertEquals(2, SUT.articles.value?.size )
        assertEquals(article1, SUT.articles.value?.get(1) )
    }


}