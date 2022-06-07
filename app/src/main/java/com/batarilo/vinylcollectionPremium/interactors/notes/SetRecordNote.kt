package com.batarilo.vinylcollectionPremium.interactors.notes

import com.batarilo.vinylcollectionPremium.data.model.Record
import com.batarilo.vinylcollectionPremium.data.room.RecordDao

class SetRecordNote(private val recordDao: RecordDao) {
    suspend fun execute(record:Record, note: String?){
        record.note = note
        println("Updating record $record")
        recordDao.updateRecord(record)
    }
}