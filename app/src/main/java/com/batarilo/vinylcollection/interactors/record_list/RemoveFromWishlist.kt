package com.batarilo.vinylcollection.interactors.record_list

import com.batarilo.vinylcollection.data.room.RecordDao

class RemoveFromWishlist(val recordDao: RecordDao) {
    suspend fun execute(recordId:Int){

       recordDao.deleteRecordFromWishlist(recordId)
    }
}