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

    @Query("SELECT * FROM record_table WHERE belongsTo='WISHLIST'")
    fun readWishList():LiveData<List<Record>>

    @Query("SELECT * FROM record_table WHERE belongsTo='COLLECTION'")
    fun readCollection():LiveData<List<Record>>

    @Delete
    suspend fun deleteRecord(record: Record)
}