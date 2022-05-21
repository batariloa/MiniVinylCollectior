package com.batarilo.vinylcollection.data.model

import androidx.room.*
import com.batarilo.vinylcollection.data.room.RecordConverters
import java.io.Serializable

@Entity(tableName = "record_table",
    foreignKeys = [ForeignKey(
        entity = RecordData::class,
        parentColumns = ["id_data"],
        childColumns = ["id_data"]
    )])
data class Record (

    @PrimaryKey
    val id: Long,
    var id_data:Long?,

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


data class RecordInList(

    @Embedded
    val record: Record,

    @Relation(
        parentColumn = "id",
        entityColumn = "id_data",
        entity = RecordData::class
    )
    val recordData: RecordData?


)



@Entity(tableName = "record_data",
    indices = [Index(value = ["id_data","belongsTo"], unique = true)],
)
data class RecordData(

    @PrimaryKey(autoGenerate = true)
    var id_data: Long,

    val belongsTo: ListType
)

enum class ListType {COLLECTION, WISHLIST, HISTORY, CACHE}