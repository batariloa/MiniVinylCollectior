package com.batarilo.vinylcollection.data.model

import androidx.room.*
import com.batarilo.vinylcollection.data.room.RecordConverters
import java.io.Serializable

@Entity(tableName = "record_table")
data class Record (

    @PrimaryKey
    val id: Int?,

    @TypeConverters(RecordConverters::class)
    val format: List<String>?,
    @TypeConverters(RecordConverters::class)
    val genre: List<String>?,
    @TypeConverters(RecordConverters::class)
    val label: List<String>?,

    var note:String?, //added note


    val catno: String?,
    val country: String?,
    val cover_image: String?,
    val format_quantity: Int,
    val thumb: String?,
    val title: String?,
    val type: String?,
    val uri: String?,
    val year: String?
): Serializable

@Entity(tableName = "record_in_list",
    indices = [Index(value = ["id","belongsTo"], unique = true)]
)
data class RecordInList(
    @PrimaryKey(autoGenerate = true)
    var id_record_listed: Int,
    @Embedded
    val record: Record,
    val belongsTo:ListType

)

enum class ListType {COLLECTION, WISHLIST, HISTORY}