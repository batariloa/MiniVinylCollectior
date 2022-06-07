package com.batarilo.vinylcollectionPremium.interactors.cache

import android.content.Context
import com.batarilo.vinylcollectionPremium.data.room.RecordDao
import com.bumptech.glide.Glide

class DeleteCache(private val recordDao: RecordDao) {

    suspend fun execute(context: Context){
        Glide.get(context).clearDiskCache()
        recordDao.deleteCache()
    }
}