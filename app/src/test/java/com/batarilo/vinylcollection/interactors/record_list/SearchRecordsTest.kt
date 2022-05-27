package com.batarilo.vinylcollection.interactors.record_list

import com.batarilo.vinylcollection.data.DatabaseFake
import com.batarilo.vinylcollection.data.RecordDaoFake
import com.batarilo.vinylcollection.data.model.RecordInList
import com.batarilo.vinylcollection.data.retrofit.DiscogApiService
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class SearchRecordsTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl
    val dummyQuery = "Zeppelin"

    //system in test
    private lateinit var searchRecordsApi:SearchRecordsApi

    //dependencies
    private lateinit var discogService: DiscogApiService
    private lateinit var databaseFake: DatabaseFake
    private lateinit var recordDaoFake: RecordDaoFake

    @BeforeEach
    fun setup(){

        mockWebServer = MockWebServer()
        mockWebServer.start()

        baseUrl = mockWebServer.url("/")

        discogService = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(DiscogApiService::class.java)

        databaseFake = DatabaseFake()
        recordDaoFake = RecordDaoFake(databaseFake)

        //ready to instantiate system
        searchRecordsApi= SearchRecordsApi(
            recordDaoFake,
            discogService
        )
    }

    @AfterEach
    fun tearDown(){
        mockWebServer.shutdown()
    }

    /**
     * What we are testing:
     * 1. Are records retrieved from the network? (Check if cache is empty, then check if its filled)
     * 2. Are the records inserted into the cache?
     * 3. Are the records then emitted to the UI? (as a flow)
     */
    @Test
    fun geRecordsFromNetwork_emitRecordFromCache()  {
        runBlocking {
            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .addHeader("Content-Type", "application/json")
                    .setBody(WebMockServerResponse.recordListResponse)
            )


            println("Tell me if its empty "+ recordDaoFake.readAllData().isEmpty())
            //confirm that cache is empty
            assert(recordDaoFake.readAllData().isEmpty())

            val flowItems = searchRecordsApi.execute("", true, cacheOn = true).toList()


            println("HERES ALL THE DATA " + recordDaoFake.readAllData())
            //confirm cache

            assert(recordDaoFake.readAllData().isNotEmpty())

            //first emission should be loading
            assert(flowItems[0].loading)

            //result is emitted
            val records = flowItems[1].data
            assert(records?.size?:0>0)

            //confirm they are Record objects
            assert(records?.get(0) is RecordInList)

            //ensure loading is false now
            assert(!flowItems[1].loading)
        }
        }

    @Test
    fun getRecipesFromNetwork_emitHttpError():Unit= runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .setBody("{}")
        )

        val flowItems = searchRecordsApi.execute(dummyQuery,
            isNetworkAvailable = true,
            cacheOn = true
        ).toList()
        assert(flowItems[0].loading)
        assert(flowItems[1].error!=null)

    }



}