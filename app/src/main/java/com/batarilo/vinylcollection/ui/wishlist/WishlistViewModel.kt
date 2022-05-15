package com.batarilo.vinylcollection.ui.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.data.room.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    val recordRepository: RecordRepository
)
    : ViewModel() {

        fun removeRecord(record: Record){
            viewModelScope.launch(Dispatchers.IO) {
                recordRepository.removeRecordFromWishlist(record)
            }
        }

        fun loadWishList(): LiveData<List<Record>> {
              return  recordRepository.readFromWishlist()
        }



}