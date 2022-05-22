package com.batarilo.vinylcollection.interactors.record_list

import com.batarilo.vinylcollection.data.model.RecordInList
import com.batarilo.vinylcollection.data.room.RecordDao
import com.batarilo.vinylcollection.data.room.cache.DataState
import com.batarilo.vinylcollection.ui.wishlist.RecordAdapterWishlist
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class ReadAllFromWishlist(private val recordDao: RecordDao) {

    fun execute(): Flow<DataState<List<RecordInList>>> = flow{
        emit(DataState(recordDao.readAllFromWishlist()))
    }
}