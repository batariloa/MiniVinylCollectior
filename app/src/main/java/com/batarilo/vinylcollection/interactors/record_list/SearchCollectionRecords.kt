package com.batarilo.vinylcollection.interactors.record_list

import android.service.autofill.Dataset
import com.batarilo.vinylcollection.data.model.RecordInList
import com.batarilo.vinylcollection.data.room.RecordDao
import com.batarilo.vinylcollection.data.room.cache.DataState
import com.batarilo.vinylcollection.ui.collection.recycle.RecordAdapterCollection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class SearchCollectionRecords(private val recordDao: RecordDao) {

    fun execute(query:String): Flow<DataState<List<RecordInList>>> = flow{
        emit(DataState(recordDao.searchCollection(query)))
    }
}