package com.batarilo.vinylcollection.data.room

import androidx.room.*
import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.data.model.RecordData
import com.batarilo.vinylcollection.data.model.RecordInList

@Dao
    abstract class RecordDao {

suspend fun insertAll(recordsInList:List<RecordInList>){

    println("INSERT ALL JE $recordsInList")
    recordsInList.forEach{
        insertRecordId(it)
    }
}
    suspend fun insertRecordId(recordInList: RecordInList){


        var idData = recordInList.recordData?.let { addRecordData(it) }
        println("Dodajem record sa datom ${recordInList.record.id}")
        recordInList.record.id_data = idData
        addRecord(recordInList.record)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
  abstract suspend fun addRecord(record: Record)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addRecordData(recordData: RecordData): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addRecordDataList(recordData: List<RecordData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addRecords(list:List<Record>):LongArray

    @Query("SELECT * FROM record_table JOIN record_data ON record_table.id_data=record_data.id_data")
   abstract suspend fun readAllData():List<RecordInList>

    @Query("SELECT * FROM record_table JOIN record_data" +
            " ON record_table.id_data=record_data.id_data WHERE title LIKE '%' || :query || '%'")
    abstract suspend fun searchRecords(query:String):List<RecordInList>

}