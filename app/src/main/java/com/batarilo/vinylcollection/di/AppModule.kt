package com.batarilo.vinylcollection.di

import android.content.Context
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
    fun provideStringTest():String{
        return "blop"
    }

}