package com.batarilo.vinylcollection.data.model

import androidx.room.*
import com.batarilo.vinylcollection.data.room.RecordConverters
import java.io.Serializable

@Entity(tableName = "record_table", primaryKeys = ["id"])
data class Record (


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


data class RecordInList(

    @Embedded
    val record: Record,

    @Relation(
        parentColumn = "id",
        entityColumn = "id_record_listed",
        entity = RecordData::class
    )
    val recordData: RecordData


)

class RecordHolder{
    @Entity(primaryKeys = ["day", "recipe"],
        foreignKeys = [ForeignKey(entity = Record::class, parentColumns = ["id"], childColumns = ["record"], onDelete = ForeignKey.CASCADE),
            ForeignKey(entity = RecordData::class, parentColumns = ["id"], childColumns = ["data"], onDelete = ForeignKey.CASCADE)])
    class AssignedRecord {
        var record: Int = 0
        var data: Int = 0
    }

}

@Entity(tableName = "record_data",
    indices = [Index(value = ["id_record_listed","belongsTo"], unique = true)],
    primaryKeys = ["id"]
)
data class RecordData(

    var id: Int,

    val belongsTo: ListType
)

enum class ListType {COLLECTION, WISHLIST, HISTORY, CACHE}