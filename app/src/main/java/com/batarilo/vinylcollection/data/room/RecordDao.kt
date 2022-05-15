package com.batarilo.vinylcollection.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.data.model.RecordType

@Dao
interface RecordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecord(record:Record)

    @Query("SELECT * FROM record_table ORDER BY id ASC")
    fun readAllData():LiveData<List<Record>>

    @Query("SELECT * FROM record_table WHERE inWishlist=1")
    fun readWishList():LiveData<List<Record>>

    @Query("SELECT * FROM record_table WHERE inCollection=1")
    fun readCollection():LiveData<List<Record>>

    @Delete
    suspend fun deleteRecord(record: Record)

    @Update
    suspend fun updateRecord(record: Record)
}