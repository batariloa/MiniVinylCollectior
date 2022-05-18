package com.batarilo.vinylcollection.data

import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.data.model.RecordInList


class DatabaseFake {
    val records = mutableListOf<Record>()

    val recordsInList = mutableListOf<RecordInList>()
}