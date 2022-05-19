package com.batarilo.vinylcollection.data

import com.batarilo.vinylcollection.data.model.ListType
import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.data.model.RecordInList


class DatabaseFake {
    val records = mutableListOf<Record>()
    val recordsInList = mutableListOf(RecordInList(
        123,
        Record(3213,
            null,
            listOf("Yo"),
            listOf("Heh"),
            "Note",
            "Catno",
            "Serbia",
            "coverimage.jpg",
            30,
            "thumb",
            "title",
            "type",
            "URI",
            "1987"),ListType.COLLECTION))
}