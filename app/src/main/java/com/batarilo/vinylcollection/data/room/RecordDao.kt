package com.batarilo.vinylcollection.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.batarilo.vinylcollection.data.model.Record

@Dao
interface RecordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRecord(record:Record)

    @Query("SELECT * FROM record_table ORDER BY id ASC")
    fun readAllData():LiveData<List<Record>>
}