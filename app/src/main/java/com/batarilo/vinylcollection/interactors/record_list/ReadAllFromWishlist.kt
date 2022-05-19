package com.batarilo.vinylcollection.interactors.record_list

import com.batarilo.vinylcollection.data.room.RecordDao
import com.batarilo.vinylcollection.ui.wishlist.RecordAdapterWishlist
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReadAllFromWishlist(private val recordDao: RecordDao) {

    suspend fun execute(recordAdapterWishlist:RecordAdapterWishlist) {

     val records = recordDao.readWishList()
      recordAdapterWishlist.records= records

    }

}