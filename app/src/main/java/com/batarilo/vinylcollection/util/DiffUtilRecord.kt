package com.batarilo.vinylcollection.util

import androidx.recyclerview.widget.DiffUtil
import com.batarilo.vinylcollection.data.model.RecordInList

class DiffUtilRecord(
    private val oldList: List<RecordInList>,
    private val newList: List<RecordInList>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] != newList[newItemPosition]
    }
}