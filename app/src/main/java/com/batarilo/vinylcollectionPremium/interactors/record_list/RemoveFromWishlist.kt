package com.batarilo.vinylcollectionPremium.interactors.record_list

import com.batarilo.vinylcollectionPremium.data.room.RecordDao

class RemoveFromWishlist(val recordDao: RecordDao) {
    suspend fun execute(recordId:Int){

       recordDao.deleteRecordFromWishlist(recordId)
    }
}