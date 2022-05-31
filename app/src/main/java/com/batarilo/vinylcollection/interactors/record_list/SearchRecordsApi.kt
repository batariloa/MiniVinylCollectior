package com.batarilo.vinylcollection.interactors.record_list

import com.batarilo.vinylcollection.data.model.JsonResponse
import com.batarilo.vinylcollection.data.model.ListType
import com.batarilo.vinylcollection.data.model.RecordData
import com.batarilo.vinylcollection.data.model.RecordInList
import com.batarilo.vinylcollection.data.retrofit.DiscogApiService
import com.batarilo.vinylcollection.data.room.RecordDao
import com.batarilo.vinylcollection.data.room.cache.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class SearchRecordsApi(
    private val recordDao: RecordDao,
    private val discogApiService: DiscogApiService,
    ){
    fun execute(
        query:String,
        isNetworkAvailable:Boolean,
        cacheOn:Boolean
    ): Flow<DataState<List<RecordInList>>> = flow{
        try {
            emit(DataState.loading())


            println("QUERY $query")
            //for testing
            if(query=="errorForce"){
                throw Exception("Search FAILED!")}

            if(isNetworkAvailable) {
                val records = getRecordFromNetwork(query)
                //if cache is off
                if (!cacheOn) {
                    val recordsDirect = records.results.map { item ->
                        RecordInList(
                            item,
                            RecordData(0, item.id, ListType.CACHE)
                        )

                    }
                    println("CACHE OFF $recordsDirect")
                    emit(DataState.success(recordsDirect))
                }

                //if cache is on
                if (cacheOn) {
                    //insert into cache
                    val recordsInList =
                        records.results.map { RecordInList(it, RecordData(0, it.id,ListType.CACHE)) }

                    //insert into cache
                    recordDao.insertAll(recordsInList)
                    println("WHat is inserted into cache $recordsInList")


                    //query the cache
                    val cacheResult = if (query.isBlank()) {
                        getRecordFromCache(query)
                    } else
                        getRecordFromCache(query)

                    //emit list from cache
                    emit(DataState.success(cacheResult))
                }
            }
            if(!isNetworkAvailable)
                emit(DataState.success(getRecordFromCache(query)))


        }catch (e:Exception){
            println("ERROR IN SEARCH: $e")
            emit(DataState.error(e.message?:"Unknown error"))
        }
    }


    private suspend fun getRecordFromNetwork(query:String): JsonResponse {
        return discogApiService.searchDiscogResponse(
            DiscogApiService.AUTH_KEY,
            DiscogApiService.AUTH_SECRET,
            query,
            DiscogApiService.TYPE_RELEASE,

        )
    }
    private suspend fun getRecordFromCache(query: String): List<RecordInList> {
      println("REZULTAT ZA SAD JE "+ recordDao.searchRecords(query).toString())
        return recordDao.searchRecords(query)
    }
}