package com.example.lifruk.data

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.json.JSONObject

@Entity(tableName = "Word", foreignKeys = [ForeignKey(entity = Topic::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("topic"),
    onDelete = ForeignKey.CASCADE)]
)
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "language")
    var language: Language,

    @ColumnInfo(name = "content")
    var content: String,

    @ColumnInfo(name = "topic", index = true)
    var topic: Int,

    //R.drawable
    @ColumnInfo(name = "picture")
    var picture: Int
){
    fun asJSON(): JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("language", this.language)
        jsonObject.put("content", this.content)
        jsonObject.put("topic", this.topic)
        jsonObject.put("picture", this.picture)
        return jsonObject
    }
}

