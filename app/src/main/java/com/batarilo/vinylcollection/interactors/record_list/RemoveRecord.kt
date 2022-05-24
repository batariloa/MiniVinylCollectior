package com.batarilo.vinylcollection.interactors.record_list

import com.batarilo.vinylcollection.data.model.RecordInList
import com.batarilo.vinylcollection.data.room.RecordDao

class RemoveRecord(private val recordDao: RecordDao) {

    suspend fun execute(recordInList: RecordInList){
        recordInList.recordData?.let { recordDao.deleteRecordInList(it) }
    }
}