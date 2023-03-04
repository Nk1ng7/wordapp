package com.example.lifruk.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Translation", foreignKeys = [ForeignKey(entity = Word::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("englishWord"),
    onDelete = ForeignKey.CASCADE), ForeignKey(entity = Word::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("translation"),
    onDelete = ForeignKey.CASCADE)]
)
data class Translation(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "englishWord", index = true)
    var word: Int,

    @ColumnInfo(name = "translation", index = true)
    var translation: Int
)