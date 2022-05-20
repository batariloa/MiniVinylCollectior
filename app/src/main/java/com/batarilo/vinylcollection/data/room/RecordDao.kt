package com.batarilo.vinylcollection.data.room

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.data.model.RecordInList
import retrofit2.Response
import retrofit2.http.GET

@Dao
interface RecordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecordInList(record:RecordInList)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecordsInList(records:List<RecordInList>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecords(list:List<Record>):LongArray

    @Query("SELECT * FROM record_table ORDER BY id ASC")
   suspend fun readAllData():List<Record>

    @Query("SELECT * FROM record_in_list WHERE title LIKE '%' || :query || '%' AND belongsTo='CACHE'")
    suspend fun searchRecords(query:String):List<RecordInList>

    @Query("SELECT * FROM record_in_list WHERE belongsTo='COLLECTION' AND title LIKE '%' || :query || '%'")
    suspend fun searchCollection(query:String):List<RecordInList>

    @Query("SELECT * FROM record_in_list WHERE belongsTo='WISHLIST' AND title LIKE '%' || :query || '%'")
    suspend fun searchWishlist(query:String):List<RecordInList>

    @Query("SELECT * FROM record_in_list WHERE belongsTo='HISTORY' AND title LIKE '%' || :query || '%'")
    suspend fun searchHistory(query:String):List<RecordInList>

    @Query("SELECT * FROM record_in_list WHERE belongsTo='WISHLIST'")
     suspend fun readWishList():List<RecordInList>

    @Query("SELECT * FROM record_in_list WHERE belongsTo='COLLECTION'")
    suspend fun readCollection():List<RecordInList>

    @Query("SELECT * FROM record_in_list WHERE belongsTo='HISTORY' ORDER BY id_record_listed DESC")
    suspend fun readHistory():List<RecordInList>

    @Delete
    suspend fun deleteRecordInList(record: RecordInList)

    @Update
    suspend fun updateRecord(record: RecordInList)

}