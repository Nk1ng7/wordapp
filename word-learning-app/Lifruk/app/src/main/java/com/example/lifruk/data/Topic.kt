package com.example.lifruk.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Topic")
data class Topic(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "difficulty")
    var difficulty: Difficulty
)
