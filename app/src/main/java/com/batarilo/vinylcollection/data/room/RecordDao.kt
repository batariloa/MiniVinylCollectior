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
    suspend fun addRecord(record:RecordInList)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecords(list:List<Record>)


    @Query("SELECT * FROM record_table ORDER BY id ASC")
   suspend fun readAllData():List<Record>

    @Query("SELECT * FROM record_table WHERE title LIKE '%' || :query || '%'")
    suspend fun searchRecords(query:String):List<Record>


    @Query("SELECT * FROM record_in_list WHERE belongsTo='WISHLIST'")
     fun readWishList():LiveData<List<RecordInList>>

    @Query("SELECT * FROM record_in_list WHERE belongsTo='COLLECTION'")
    fun readCollection():LiveData<List<RecordInList>>

    @Query("SELECT * FROM record_in_list WHERE belongsTo='HISTORY'")
    fun readHistory():LiveData<List<RecordInList>>

    @Query("SELECT * FROM record_table WHERE id=:id")
     fun findRecordById(id:Int):LiveData<Record>

     @Query("SELECT EXISTS(SELECT * FROM record_table WHERE id=:id)")
     fun recordExists(id:Int):Boolean





    @Delete
    suspend fun deleteRecordInList(record: RecordInList)

    @Update
    suspend fun updateRecord(record: RecordInList)

}