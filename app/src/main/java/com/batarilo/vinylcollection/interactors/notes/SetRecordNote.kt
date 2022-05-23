package com.batarilo.vinylcollection.interactors.notes

import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.data.room.RecordDao

class SetRecordNote(private val recordDao: RecordDao) {
    suspend fun execute(record:Record, note: String?){
        record.note = note
        recordDao.updateRecord(record)
    }
}