package com.batarilo.vinylcollection.data.room

import androidx.lifecycle.LiveData
import com.batarilo.vinylcollection.data.model.Record
import javax.inject.Inject

class RecordRepository  @Inject constructor(private val recordDao: RecordDao) {

   fun readAllData(): LiveData<List<Record>>{
       return recordDao.readAllData()
   }

    suspend fun addRecord(record: Record){
        recordDao.addRecord(record)
    }
}