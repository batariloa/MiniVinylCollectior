package com.batarilo.vinylcollection.di

import android.content.Context
import com.batarilo.vinylcollection.data.retrofit.RecordApiService
import com.batarilo.vinylcollection.data.retrofit.RetrofitInstance
import com.batarilo.vinylcollection.data.room.RecordDao
import com.batarilo.vinylcollection.data.room.RecordDatabase
import com.batarilo.vinylcollection.data.room.RecordRepository
import com.batarilo.vinylcollection.interactors.record_list.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context):VinylApp{
        return app as VinylApp
    }


    @Provides
    fun provideString():String{
        return "blbl"
    }



    @Provides
    fun provideRecordDatabase(@ApplicationContext app:Context): RecordDatabase {
        return RecordDatabase.getDatabase(app)
    }

    @Provides
    @Singleton
    fun provideRecordDao(recordDatabase: RecordDatabase): RecordDao {
        return recordDatabase.recordDao()
    }
    @Provides
    fun provideApi(): RecordApiService {
        return RetrofitInstance.api
    }
    @Provides
    @Singleton
    fun provideRecordRepository(recordDao: RecordDao): RecordRepository{
        return RecordRepository(recordDao, RetrofitInstance.api)
    }
    @Provides
    fun provideSearchRecordUseCase(recordDao: RecordDao, recordApiService: RecordApiService): SearchRecordsApi {
        return SearchRecordsApi(recordDao,recordApiService)
    }
    @Provides
    fun provdideReadAllFromCollectionUseCase(recordDao: RecordDao): ReadAllFromCollection {
        return ReadAllFromCollection(recordDao)
    }
    @Provides
    fun searchFromCollectionUseCase(recordDao: RecordDao): SearchCollectionRecords {
        return SearchCollectionRecords(recordDao)
    }
    @Provides
    fun provdideReadAllFromHistoryUseCase(recordDao: RecordDao): ReadAllFromHistory {
        return ReadAllFromHistory(recordDao)
    }
    @Provides
    fun searchFromHistoryUseCase(recordDao: RecordDao): SearchHistoryRecords {
        return SearchHistoryRecords(recordDao)
    }

    @Provides fun provideSearchWishlistUseCase(recordDao: RecordDao): SearchWishlist {
        return SearchWishlist(recordDao)
    }

    @Provides
    fun provideReadAllFromWishlistUseCase(recordDao: RecordDao): ReadAllFromWishlist {
        return ReadAllFromWishlist(recordDao)
    }

    @Provides
    fun provideAddToWishlist(recordDao: RecordDao): AddToWishList {
        return AddToWishList(recordDao)
    }


}