package com.batarilo.vinylcollection.data.room

import androidx.lifecycle.LiveData
import com.batarilo.vinylcollection.data.model.ListType
import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.data.model.RecordInList
import com.batarilo.vinylcollection.data.retrofit.RecordApiService
import javax.inject.Inject


class RecordRepository  @Inject constructor(private val recordDao: RecordDao,
                                            private val apiService: RecordApiService
) {










}