package com.batarilo.vinylcollection.data.room

import androidx.room.*
import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.data.model.RecordData
import com.batarilo.vinylcollection.data.model.RecordInList

@Dao
    abstract class RecordDao {

suspend fun insertAll(recordsInList:List<RecordInList>){

    recordsInList.forEach{
        insertRecord(it)
    }
}
    suspend fun insertRecord(recordInList: RecordInList){

        var idRecord = addRecord(recordInList.record)
        recordInList.recordData?.id_record = idRecord.toInt()
        recordInList.recordData?.let { addRecordData(it) }
        println("Insert record with data: ${recordInList.record.id}")


    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addRecord(record: Record): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addRecordData(recordData: RecordData): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addRecordDataList(recordData: List<RecordData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addRecords(list:List<Record>):LongArray

    @Transaction
    @Query("SELECT * FROM record_table JOIN record_data" +
            " ON record_table.id=record_data.id_record " +
            "WHERE record_data.belongsTo='WISHLIST'")
    abstract suspend fun readAllFromWishlist():List<RecordInList>

    @Transaction
    @Query("SELECT *, record_table.id as id FROM record_table JOIN record_data" +
            " ON record_table.id=record_data.id_record " +
            "WHERE record_data.belongsTo='COLLECTION'")
    abstract suspend fun readAllFromCollection():List<RecordInList>

    @Query("SELECT *, record_table.id as id FROM record_table JOIN record_data" +
            " ON record_table.id=record_data.id_record " +
            "WHERE record_data.belongsTo='HISTORY'")
    abstract suspend fun readAllFromHistory():List<RecordInList>

    @Query("SELECT *, record_table.id as id  FROM record_table JOIN record_data" +
            " ON record_table.id=record_data.id_record")
   abstract suspend fun readAllData():List<RecordInList>

    @Query("SELECT *, record_table.id as id  FROM record_table JOIN record_data" +
            " ON record_table.id=record_data.id_record WHERE title LIKE '%' || :query || '%'" +
            "AND record_data.belongsTo='CACHE'")
    abstract suspend fun searchRecords(query:String):List<RecordInList>

    @Query("SELECT *, record_table.id as id  FROM record_table JOIN record_data" +
            " ON record_table.id=record_data.id_record WHERE title LIKE '%' || :query || '%'" +
            "AND record_data.belongsTo='COLLECTION'")
    abstract suspend fun searchCollection(query:String):List<RecordInList>

    @Query("SELECT *, record_table.id as id  FROM record_table JOIN record_data" +
            " ON record_table.id=record_data.id_record WHERE title LIKE '%' || :query || '%'" +
            "AND record_data.belongsTo='WISHLIST'")
    abstract suspend fun searchWishlist(query:String):List<RecordInList>

    @Query("SELECT *, record_table.id as id  FROM record_table JOIN record_data" +
            " ON record_table.id=record_data.id_record WHERE title LIKE '%' || :query || '%'" +
            "AND record_data.belongsTo='HISTORY'")
    abstract suspend fun searchHistory(query:String):List<RecordInList>

    @Update
    abstract suspend fun updateRecord(record: Record)

    @Transaction
    @Query("DELETE FROM record_data WHERE belongsTo = 'CACHE'" )
    abstract suspend fun deleteCache()

    @Delete
    abstract suspend fun deleteRecordInList(recordData: RecordData)

}