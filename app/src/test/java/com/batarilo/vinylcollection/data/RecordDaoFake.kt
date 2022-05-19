package com.batarilo.vinylcollection.data

import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.data.model.RecordInList
import com.batarilo.vinylcollection.data.room.RecordDao

class RecordDaoFake (
    private val databaseFake: DatabaseFake
    ): RecordDao{
    override suspend fun addRecordInList(record: RecordInList) {
        TODO("Not yet implemented")
    }


    override suspend fun addRecords(list: List<Record>): LongArray {
        databaseFake.records.addAll(list)
        return longArrayOf(1)

    }

    override suspend fun readAllData(): List<Record> {
        return databaseFake.records
    }

    override suspend fun searchRecords(query: String): List<Record> {
        return databaseFake.records //return all records to keep it simple
    }

    override suspend fun searchCollection(query: String): List<RecordInList> {
        return databaseFake.recordsInList //return all records to keep it simple

    }

    override suspend fun searchWishlist(query: String): List<RecordInList> {
        return databaseFake.recordsInList //return all records to keep it simple
    }

    override suspend fun searchHistory(query: String): List<RecordInList> {
        return databaseFake.recordsInList //return all records to keep it simple

    }

    override suspend fun readWishList(): List<RecordInList> {
        return databaseFake.recordsInList //return all records to keep it simple

    }

    override suspend fun readCollection(): List<RecordInList> {
        return databaseFake.recordsInList //return all records to keep it simple
    }

    override suspend fun readHistory(): List<RecordInList> {
        return databaseFake.recordsInList //return all records to keep it simple
    }


    override suspend fun deleteRecordInList(record: RecordInList) {
        TODO("Not yet implemented")
    }

    override suspend fun updateRecord(record: RecordInList) {
        TODO("Not yet implemented")
    }

}