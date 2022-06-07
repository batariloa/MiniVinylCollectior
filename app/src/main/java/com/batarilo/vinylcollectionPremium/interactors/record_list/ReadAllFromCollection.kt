package com.batarilo.vinylcollectionPremium.interactors.record_list

import com.batarilo.vinylcollectionPremium.data.model.RecordInList
import com.batarilo.vinylcollectionPremium.data.room.RecordDao
import com.batarilo.vinylcollectionPremium.data.room.cache.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ReadAllFromCollection(val recordDao: RecordDao) {
    fun execute(): Flow<DataState<List<RecordInList>>> = flow{
        println("READ ALL FROM COLLECTION USE CASE" + recordDao.readAllFromCollection())
        emit(DataState(recordDao.readAllFromCollection()))

    }

}