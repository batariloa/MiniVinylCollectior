package com.batarilo.vinylcollection.interactors.record_list

import com.batarilo.vinylcollection.data.model.RecordInList
import com.batarilo.vinylcollection.data.room.RecordDao

class SearchHistoryRecords(private val recordDao: RecordDao) {


    suspend fun execute(query:String): List<RecordInList> {

        return recordDao.searchRecords(query)
    }

}

