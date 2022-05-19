package com.batarilo.vinylcollection.UI

import com.batarilo.vinylcollection.ui.collection.recycle.RecordAdapterCollection
import com.batarilo.vinylcollection.ui.search.recycle.RecordAdapterSearch
import com.batarilo.vinylcollection.ui.wishlist.RecordAdapterWishlist

class OnRecordListenerFake
    : RecordAdapterWishlist.OnRecordListenerWishlist


{


    override fun onRecordClicked(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onRemoveClicked(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onAddToNotesClicked(position: Int) {
        TODO("Not yet implemented")
    }
}