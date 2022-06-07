package com.batarilo.vinylcollectionPremium.data

import com.batarilo.vinylcollectionPremium.data.model.ListType
import com.batarilo.vinylcollectionPremium.data.model.Record
import com.batarilo.vinylcollectionPremium.data.model.RecordData
import com.batarilo.vinylcollectionPremium.data.model.RecordInList


class DatabaseFake {
    val records = mutableListOf<Record>()
    val recordsInList = arrayListOf<RecordInList>()
     val record = RecordInList(

        Record(3213,
            0,
            listOf("Yo"),
            listOf("Heh"),
            listOf(),
            "Catno",
            "Serbia",
            "coverimage.jpg",
            null,
           0 ,
            "title",
            "type",
            "URI",
            "1987",
        "1999"), RecordData(0,0,ListType.CACHE)
    )
}