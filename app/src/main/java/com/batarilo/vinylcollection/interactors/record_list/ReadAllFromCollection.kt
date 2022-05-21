package com.batarilo.vinylcollection.interactors.record_list

import com.batarilo.vinylcollection.data.model.RecordInList
import com.batarilo.vinylcollection.data.room.RecordDao
import com.batarilo.vinylcollection.ui.collection.recycle.RecordAdapterCollection

class ReadAllFromCollection(val recordDao: RecordDao) {
    suspend fun execute(): List<RecordInList> {

        return listOf()


    }
}