package com.example.lifruk.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Game", foreignKeys = [ForeignKey(entity = Player::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("player"),
    onDelete = ForeignKey.CASCADE), ForeignKey(entity = Topic::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("topic"),
    onDelete = ForeignKey.CASCADE)]
)
data class Game(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "player", index = true)
    var player: Int,

    @ColumnInfo(name = "topic", index = true)
    var topic: Int,

    @ColumnInfo(name = "type")
    var type: Type
)