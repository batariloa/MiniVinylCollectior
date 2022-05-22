package com.batarilo.vinylcollection.interactors.record_list

import com.batarilo.vinylcollection.data.model.ListType
import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.data.model.RecordData
import com.batarilo.vinylcollection.data.model.RecordInList
import com.batarilo.vinylcollection.data.room.RecordDao

class AddToHistory(private val recordDao: RecordDao) {
    suspend fun execute(record: Record){
        recordDao.insertRecord(RecordInList(record, RecordData(0, ListType.HISTORY)))
    }
}