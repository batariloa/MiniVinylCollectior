package com.batarilo.vinylcollection.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.batarilo.vinylcollection.data.room.RecordConverters

@Entity(tableName = "record_table")
data class Record (

    @PrimaryKey
    val id: Int,

    @TypeConverters(RecordConverters::class)
    val format: List<String>?,
    @TypeConverters(RecordConverters::class)
    val genre: List<String>?,
    @TypeConverters(RecordConverters::class)
    val label: List<String>?,

    var belongsTo: RecordType, //Place to be saved


    val catno: String?,
    val country: String?,
    val cover_image: String?,
    val format_quantity: Int,
    val thumb: String?,
    val title: String?,
    val type: String?,
    val uri: String?,
    val year: String?
)

enum class RecordType {COLLECTION, WISHLIST}