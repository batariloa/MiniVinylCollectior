package com.batarilo.vinylcollectionPremium.interactors.record_list

import com.batarilo.vinylcollectionPremium.data.model.RecordInList
import com.batarilo.vinylcollectionPremium.data.room.RecordDao
import com.batarilo.vinylcollectionPremium.data.room.cache.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchWishlist(private val recordDao: RecordDao)
{
    fun execute(query:String): Flow<DataState<List<RecordInList>>> = flow{
        emit(DataState(recordDao.searchWishlist(query)))
    }
}