package com.batarilo.vinylcollectionPremium.interactors.record_list

import com.batarilo.vinylcollectionPremium.data.room.RecordDao
import com.batarilo.vinylcollectionPremium.data.room.cache.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RecordExistsInCollection(private val recordDao: RecordDao) {
    fun execute(id:Int): Flow<DataState<Boolean>> = flow {
        emit(DataState(recordDao.recordIdExistsInCollection(id)))
    }
}