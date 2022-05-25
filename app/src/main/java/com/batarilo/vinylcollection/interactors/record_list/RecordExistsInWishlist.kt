package com.batarilo.vinylcollection.interactors.record_list

import com.batarilo.vinylcollection.data.room.RecordDao
import com.batarilo.vinylcollection.data.room.cache.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RecordExistsInWishlist(private val recordDao: RecordDao) {
    fun execute(id:Int): Flow<DataState<Boolean>> = flow {
        emit(DataState(recordDao.recordIdExistsInWishlist(id)))
    }
}