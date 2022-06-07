package com.batarilo.vinylcollectionPremium.interactors.record_list

import com.batarilo.vinylcollectionPremium.data.model.RecordInList
import com.batarilo.vinylcollectionPremium.data.room.RecordDao

class RemoveRecord(private val recordDao: RecordDao) {

    suspend fun execute(recordInList: RecordInList){
        println("REMOVE RECORD ${recordInList.recordData}")
        recordInList.recordData?.let { recordDao.deleteRecord(recordInList.recordData) }
    }
}