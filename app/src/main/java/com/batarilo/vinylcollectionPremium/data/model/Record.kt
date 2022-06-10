package com.batarilo.vinylcollectionPremium.data.model

import androidx.annotation.Keep
import androidx.room.*
import com.batarilo.vinylcollectionPremium.data.room.RecordConverters
import java.io.Serializable

@Keep
@Entity(tableName = "record_table",)
data class Record (


    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val id_spoj:Int,

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

@Keep
data class RecordInList(

    @Embedded
    val record: Record,

   @Embedded
    val recordData: RecordData?
)

@Entity(tableName = "record_data",

    foreignKeys = [ForeignKey(
        entity = Record::class,
        parentColumns = ["id"],
        childColumns = ["id_record"],),
    ],indices = [Index(value = ["id_record","belongsTo"], unique = true)]
)
@Keep
data class RecordData(
    @PrimaryKey(autoGenerate = true)
    var id_data:Int,
    var id_record: Int,
    val belongsTo: ListType)

enum class ListType {COLLECTION, WISHLIST, HISTORY, CACHE}

