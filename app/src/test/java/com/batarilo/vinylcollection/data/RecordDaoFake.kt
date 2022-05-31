package com.batarilo.vinylcollection.data

import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.data.model.RecordData
import com.batarilo.vinylcollection.data.model.RecordInList
import com.batarilo.vinylcollection.data.room.RecordDao

class RecordDaoFake (
    private val databaseFake: DatabaseFake
    ): RecordDao() {


    override suspend fun addRecordReplace(record: Record): Long {

        databaseFake.recordsInList.add(databaseFake.record)
        return Long.MAX_VALUE
    }


    override suspend fun addRecordData(recordData: RecordData): Long {
      return Long.MAX_VALUE
    }

    override suspend fun addRecordDataList(recordData: List<RecordData>) {

    }

    override suspend fun addRecords(list: List<Record>): LongArray {
        return LongArray(1)
    }

    override suspend fun readAllFromWishlist(): List<RecordInList> {
        return databaseFake.recordsInList
    }

    override suspend fun readAllFromCollection(): List<RecordInList> {
        return databaseFake.recordsInList
    }

    override suspend fun readAllFromHistory(): List<RecordInList> {
        return databaseFake.recordsInList
    }

    override suspend fun readAllData(): List<RecordInList> {
        return databaseFake.recordsInList
    }

    override suspend fun searchRecords(query: String): List<RecordInList> {
        return databaseFake.recordsInList
    }

    override suspend fun searchCollection(query: String): List<RecordInList> {
        return databaseFake.recordsInList
    }

    override suspend fun searchWishlist(query: String): List<RecordInList> {
        return databaseFake.recordsInList
    }

    override suspend fun searchHistory(query: String): List<RecordInList> {
        return databaseFake.recordsInList
    }

    override suspend fun updateRecord(record: Record) {

    }

    override suspend fun deleteCache() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteRecord(recordData: RecordData) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteRecordCollection(recordId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun recordIdExistsInCollection(id: Int): Boolean {
        return true
    }

    override suspend fun recordIdExistsInWishlist(id: Int): Boolean {
        return true
    }

    override suspend fun deleteRecordFromWishlist(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteRecordFromCollection(id: Int) {
        TODO("Not yet implemented")
    }

}