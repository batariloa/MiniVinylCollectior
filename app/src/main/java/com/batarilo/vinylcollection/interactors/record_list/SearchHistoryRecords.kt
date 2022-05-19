package com.batarilo.vinylcollection.interactors.record_list

import com.batarilo.vinylcollection.data.model.RecordInList
import com.batarilo.vinylcollection.data.room.RecordDao
import com.batarilo.vinylcollection.data.room.cache.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class SearchHistoryRecords(private val recordDao: RecordDao) {


    fun execute(query:String): Flow<DataState<List<RecordInList>>> = flow{

        try{
            emit(DataState.loading())

            val records = recordDao.searchHistory(query)

            emit(DataState.success(records))

        }
        catch (e: Exception){
            emit(DataState.error(e.message?:"Unknown error"))
            println("Here is the errror: $e")

        }
    }
}