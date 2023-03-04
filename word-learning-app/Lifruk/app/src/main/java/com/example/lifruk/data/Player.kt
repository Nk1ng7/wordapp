package com.example.lifruk.data

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lifruk.webserver.LifrukAPI
import org.json.JSONObject

@Entity(tableName = "Player")
data class Player(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "username")
    var username: String,

    @ColumnInfo(name = "email")
    var email: String,

    @ColumnInfo(name = "pwd")
    var pwd: String,

    @ColumnInfo(name = "native_l")
    var native_l: Language,

    @ColumnInfo(name = "learning_l")
    var learning_l: Language,

    @ColumnInfo(name = "dlevel")
    var dlevel: Difficulty
) {
    fun asJSON(): JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("username", this.username)
        jsonObject.put("email", this.email)
        jsonObject.put("pwd", this.pwd)
        jsonObject.put("native_l", fromLanguage(this.native_l))
        jsonObject.put("learning_l", fromLanguage(this.learning_l))
        jsonObject.put("dlevel", fromDifficulty(this.dlevel))
        return jsonObject
    }
}
