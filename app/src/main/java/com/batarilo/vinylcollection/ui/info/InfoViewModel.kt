package com.batarilo.vinylcollection.ui.info

import androidx.lifecycle.ViewModel
import com.batarilo.vinylcollection.data.model.Record
import com.batarilo.vinylcollection.data.room.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val recordRepository: RecordRepository
)
    : ViewModel() {





}