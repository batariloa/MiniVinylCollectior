package com.batarilo.vinylcollectionPremium.di

import android.content.Context
import com.batarilo.vinylcollectionPremium.data.retrofit.DiscogApiService
import com.batarilo.vinylcollectionPremium.data.retrofit.RetrofitInstance
import com.batarilo.vinylcollectionPremium.data.room.RecordDao
import com.batarilo.vinylcollectionPremium.data.room.RecordDatabase
import com.batarilo.vinylcollectionPremium.interactors.cache.DeleteCache
import com.batarilo.vinylcollectionPremium.interactors.record_list.*
import com.batarilo.vinylcollectionPremium.interactors.notes.SetRecordNote
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
    fun provideRecordDatabase(@ApplicationContext app:Context): RecordDatabase {
        return RecordDatabase.getDatabase(app)
    }
    @Provides
    @Singleton
    fun provideRecordDao(recordDatabase: RecordDatabase): RecordDao {
        return recordDatabase.recordDao()
    }
    @Provides
    fun provideApi(): DiscogApiService {
        return RetrofitInstance.API
    }
    @Provides
    fun provideSearchRecordUseCase(recordDao: RecordDao, discogApiService: DiscogApiService): SearchRecordsApi {
        return SearchRecordsApi(recordDao,discogApiService)
    }
    @Provides
    fun provdideReadAllFromCollectionUseCase(recordDao: RecordDao): ReadAllFromCollection {
        return ReadAllFromCollection(recordDao)
    }

    @Provides
    fun provideReadAllFromHistoryUseCase(recordDao: RecordDao): ReadAllFromHistory {
        return ReadAllFromHistory(recordDao)
    }
    @Provides
    fun provideReadAllFromWishlistUseCase(recordDao: RecordDao): ReadAllFromWishlist {
        return ReadAllFromWishlist(recordDao)
    }
    @Provides
    fun provideSetRecordNote(recordDao: RecordDao): SetRecordNote {
        return SetRecordNote(recordDao)
    }


    @Provides
    fun provideSearchWishlist(recordDao: RecordDao): SearchWishlist {
      return SearchWishlist(recordDao)
    }

    @Provides
    fun provideSearchCollection(recordDao: RecordDao): SearchCollectionRecords {
        return SearchCollectionRecords(recordDao)
    }

    @Provides
    fun provideAddToCollection(recordDao: RecordDao): AddToCollection {
        return AddToCollection(recordDao)
    }
    @Provides
    fun provideAddToHistory(recordDao: RecordDao): AddToHistory {
        return AddToHistory(recordDao)
    }

    @Provides
    fun provideAddToWishlist(recordDao: RecordDao): AddToWishList {
        return AddToWishList(recordDao)
    }

    @Provides
    fun provideDeleteCache(recordDao: RecordDao): DeleteCache {
        return DeleteCache(recordDao)
    }

    @Provides
    fun provideRemoveRecord(recordDao: RecordDao): RemoveRecord {
        return RemoveRecord(recordDao)
    }

    @Provides
    fun provideExistsInCollection(recordDao:RecordDao): RecordExistsInCollection {
        return RecordExistsInCollection(recordDao)
    }

    @Provides
    fun provideExistsInWishlist(recordDao:RecordDao): RecordExistsInWishlist {
        return RecordExistsInWishlist(recordDao)
    }

    @Provides
    fun provideDeleteRecordFromWishlist(recordDao: RecordDao): RemoveFromWishlist {
        return RemoveFromWishlist(recordDao)
    }
    @Provides
    fun provideDeleteRecordFromCollection(recordDao: RecordDao): RemoveFromCollection {
        return RemoveFromCollection(recordDao)
    }


}