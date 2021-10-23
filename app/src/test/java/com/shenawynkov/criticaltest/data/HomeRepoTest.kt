package com.shenawynkov.criticaltest.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.shenawynkov.criticaltest.data.home.Article
import com.shenawynkov.criticaltest.data.home.Source
import com.shenawynkov.criticaltest.data.home.TopHeadlinesResponse
import com.shenawynkov.criticaltest.networking.ApiService
import com.shenawynkov.errorhandler.BuildConfig
import com.shenawynkov.errorhandler.ErrorResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody.Companion.toResponseBody

class HomeRepoTest {


    lateinit var SUT: HomeRepo
    val apiService: ApiService = mockk()
    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()
    @ExperimentalCoroutinesApi
    private val testScope = TestCoroutineScope(testDispatcher)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    val api_key= "395771bf6aaa4f7cb4a4d691c120de88"
    @Before
    fun setUp() {
        SUT = HomeRepo(apiService)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getHeadliner_success_DataPassedToEndPointAndResponseReturned() = testScope.runBlockingTest {
        //Arrange
        val articles = mutableListOf<Article>()
        val result = MutableLiveData<ArrayList<Article>>(ArrayList())
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
        val response = TopHeadlinesResponse(articles, "success", 10)
        coEvery {
            apiService.get_Headlines(
                "BBC",
                api_key,
                1,
                10
            )
        } returns response

        //Act
        SUT.getHeadlines("BBC", result, 1, testDispatcher)
        //Assert
        coVerify { apiService.get_Headlines("BBC", "395771bf6aaa4f7cb4a4d691c120de88", 1, 10) }
        assertEquals(article, result.value?.get(0))
        assertEquals("success", SUT.status.value)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun getHeadliner_networkError_networkErrorReturned() = testScope.runBlockingTest {
        //Arrange
        val articles = mutableListOf<Article>()
        val result = MutableLiveData<ArrayList<Article>>(ArrayList())


        coEvery {
            apiService.get_Headlines(
                "BBC",
                api_key,
                1,
                10
            )
        } coAnswers
                {
                    throw  IOException()
                }


        //Act
        SUT.getHeadlines("BBC", result, 1, testDispatcher)
        //Assert
        coVerify { apiService.get_Headlines("BBC", api_key, 1, 10) }
        assertEquals("networkError", SUT.status.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getHeadliner_generalError_GeneralErrorReturned() = testScope.runBlockingTest {
        //Arrange
        val articles = mutableListOf<Article>()
        val result = MutableLiveData<ArrayList<Article>>(ArrayList())


        val errorResponse =
            "{\n" +
                    "\"status\": \"error\",\n" +
                    "\"code\": \"apiKeyMissing\",\n" +
                    "\"message\": \"Your API key is missing. Append this to the URL with the apiKey param, or use the x-api-key HTTP header.\"\n" +
                    "}"
        val errorResponseBody = errorResponse.toResponseBody("application/json".toMediaTypeOrNull())

        val mockResponse = Response.error<String>(400, errorResponseBody)
        val exception = HttpException(mockResponse)
        coEvery {
            apiService.get_Headlines(
                "BBC",
             api_key,
                1,
                10
            )
        } coAnswers {
            throw  exception
        }
            //Act
            SUT.getHeadlines("BBC", result, 1, testDispatcher)
            //Assert
            coVerify { apiService.get_Headlines("BBC", api_key, 1, 10) }
            assertEquals("error", SUT.status.value)
        }



}