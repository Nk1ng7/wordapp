package com.example.lifruk.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Answer", foreignKeys = [ForeignKey(entity = Game::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("game"),
    onDelete = ForeignKey.CASCADE), ForeignKey(entity = Word::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("word"),
    onDelete = ForeignKey.CASCADE)]
)
data class Answer(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "word", index = true)
    var word: Int,

    @ColumnInfo(name = "game", index = true)
    var game: Int,

    @ColumnInfo(name = "correct")
    var correct: Boolean,

    @ColumnInfo(name = "type")
    var type: Type
)