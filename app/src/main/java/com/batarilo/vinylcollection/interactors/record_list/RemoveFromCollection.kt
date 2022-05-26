package com.batarilo.vinylcollection.interactors.record_list

import com.batarilo.vinylcollection.data.room.RecordDao

class RemoveFromCollection(private val recordDao: RecordDao) {

    suspend fun execute(recordId:Int){
    recordDao.deleteRecordFromCollection(recordId)
    }
}