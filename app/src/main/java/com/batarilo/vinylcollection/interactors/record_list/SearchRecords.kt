package com.batarilo.vinylcollection.interactors.record_list

import android.util.Log
import com.batarilo.vinylcollection.data.model.JsonResponse
import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.data.retrofit.RecordApiService
import com.batarilo.vinylcollection.data.room.RecordDao
import com.batarilo.vinylcollection.data.room.cache.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class SearchRecords(
    private val recordDao: RecordDao,
    private val recordApiService: RecordApiService,

){
    fun execute(
        query:String,
        isNetworkAvailable:Boolean
    ): Flow<DataState<List<Record>>> = flow{
        try {
            emit(DataState.loading())

            print("IS THIS WORKING??")
            if(query=="errorForce"){
                throw Exception("Search FAILED!")}

            println("WHAT ABOUT NOW $isNetworkAvailable")

            if(isNetworkAvailable) {
                println("ABOVE RECORDS ")

                val records = getRecordFromNetwork(query)
                println("NEtWOWRK AVAILABLE $records")
                //insert into cache

                recordDao.addRecords(records.results)
            }
            println("BELOW IF ")
            //query the cache
            val cacheResult = if(query.isBlank()){
                recordDao.readAllData()
            }
            else{
                getRecordFromCache(query)
            }
            //emit list from the cache
            emit(DataState.success(cacheResult))

        }catch (e:Exception){
            println("ERROOOOR $e")
            emit(DataState.error(e.message?:"Unknown error"))
        }
    }
    private suspend fun getRecordFromNetwork(query:String): JsonResponse {
        println("Inside get network")
        return recordApiService.searchDiscogResponse(
            RecordApiService.AUTH_KEY,
            RecordApiService.AUTH_SECRET,
            query,
            RecordApiService.TYPE_RELEASE
        )
    }
    private suspend fun getRecordFromCache(query: String):List<Record>{
        return recordDao.searchRecords(query)
    }
}