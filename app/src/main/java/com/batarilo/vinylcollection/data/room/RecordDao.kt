package com.batarilo.vinylcollection.data.room

import androidx.room.*
import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.data.model.RecordData
import com.batarilo.vinylcollection.data.model.RecordHolder
import com.batarilo.vinylcollection.data.model.RecordInList

@Dao
    abstract class RecordDao {



    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun addAssignedRecord(assignedRecord: RecordHolder.AssignedRecord)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
  abstract suspend fun addRecord(record: Record)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addRecordData(recordData: RecordData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addRecordDataList(recordData: List<RecordData>)




    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addRecords(list:List<Record>):LongArray

    @Transaction
    @Query("SELECT * FROM record_table ORDER BY id ASC")
   abstract suspend fun readAllData():List<RecordInList>




}