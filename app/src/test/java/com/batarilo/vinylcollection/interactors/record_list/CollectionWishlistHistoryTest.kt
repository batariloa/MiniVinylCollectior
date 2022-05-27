package com.batarilo.vinylcollection.interactors.record_list

import com.batarilo.vinylcollection.data.DatabaseFake
import com.batarilo.vinylcollection.data.RecordDaoFake
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CollectionWishlistHistoryTest {


    //system in test
    private lateinit var searchWishlist: SearchWishlist
    private lateinit var readAllFromWishlist: ReadAllFromWishlist
    private lateinit var searchCollectionRecords: SearchCollectionRecords
    private lateinit var readAllFromCollection: ReadAllFromCollection
    private lateinit var readAllFromHistory: ReadAllFromHistory

    //dependencies
    private lateinit var databaseFake: DatabaseFake
    private lateinit var recordDaoFake: RecordDaoFake

    @BeforeEach
    fun setup(){


        databaseFake = DatabaseFake()
        recordDaoFake = RecordDaoFake(databaseFake)

        //ready to instantiate system
        searchWishlist = SearchWishlist(recordDaoFake)
        readAllFromWishlist = ReadAllFromWishlist(recordDaoFake)
        searchCollectionRecords= SearchCollectionRecords(recordDaoFake)
        readAllFromCollection = ReadAllFromCollection(recordDaoFake)
        readAllFromHistory = ReadAllFromHistory(recordDaoFake)
    }
    /**
     * What we are testing:
     */
    @Test
    fun searchRecordsFromWishlist()  {

        runBlocking {
           val records =  searchWishlist.execute("")
        }
    }

    @Test
    fun geRecordsFromWishlist()  {

        runBlocking {
            val records =  readAllFromWishlist.execute()
        }
    }


    @Test
    fun readHistoryTest(){
        runBlocking {
            val records =  readAllFromHistory.execute()
        }
    }


}