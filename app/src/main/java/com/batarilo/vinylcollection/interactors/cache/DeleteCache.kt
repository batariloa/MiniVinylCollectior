package com.batarilo.vinylcollection.interactors.cache

import com.batarilo.vinylcollection.data.room.RecordDao

class DeleteCache(private val recordDao: RecordDao) {

    suspend fun execute(){
        recordDao.deleteCache()
    }
}