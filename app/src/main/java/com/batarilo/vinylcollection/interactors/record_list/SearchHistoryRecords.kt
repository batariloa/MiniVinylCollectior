package com.batarilo.vinylcollection.interactors.record_list

import com.batarilo.vinylcollection.data.model.RecordInList
import com.batarilo.vinylcollection.data.room.RecordDao
import com.batarilo.vinylcollection.ui.collection.recycle.RecordAdapterCollection

class SearchHistoryRecords(private val recordDao: RecordDao) {


    suspend fun execute(query:String): List<RecordInList> {

        return recordDao.searchHistory(query)

    }

}