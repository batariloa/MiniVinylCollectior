package com.batarilo.vinylcollection.di

import android.content.Context
import androidx.room.Room
import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.data.room.RecordDao
import com.batarilo.vinylcollection.data.room.RecordDatabase
import com.batarilo.vinylcollection.data.room.RecordRepository
import com.batarilo.vinylcollection.ui.dialog.NoteDialog
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
    @Singleton
    fun provideRecordRepository(recordDao: RecordDao): RecordRepository{
        return RecordRepository(recordDao)
    }



}