package com.batarilo.vinylcollectionPremium.interactors.record_list

import com.batarilo.vinylcollectionPremium.data.model.ListType
import com.batarilo.vinylcollectionPremium.data.model.Record
import com.batarilo.vinylcollectionPremium.data.model.RecordData
import com.batarilo.vinylcollectionPremium.data.model.RecordInList
import com.batarilo.vinylcollectionPremium.data.room.RecordDao

class AddToHistory(private val recordDao: RecordDao) {
    suspend fun execute(record: Record){
        recordDao.insertRecord(RecordInList(record, RecordData(0, record.id, ListType.HISTORY)))
    }
}