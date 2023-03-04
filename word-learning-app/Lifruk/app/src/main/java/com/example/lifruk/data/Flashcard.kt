package com.example.lifruk.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Flashcard", foreignKeys = [ForeignKey(entity = Word::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("to_guess"),
    onDelete = ForeignKey.CASCADE), ForeignKey(entity = Player::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("player"),
    onDelete = ForeignKey.CASCADE)]
)
data class Flashcard(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "to_guess", index = true)
    var to_guess: Int,

    @ColumnInfo(name = "player", index = true)
    var player: Int
)
