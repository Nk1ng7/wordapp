package com.example.lifruk.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlayerDao {
    @Query("SELECT * FROM Player")
    fun getAllPlayers() : LiveData<List<Player>>

    @Query("SELECT * FROM Player WHERE id = :id")
    fun getPlayerById(id: Int) : LiveData<Player>

    @Query("SELECT * FROM Player WHERE email = :email and pwd = :pwd")
    fun getPlayerByEmailAndPwd(email: String, pwd: String) : LiveData<Player>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPlayer(player: Player)

    @Delete
    fun deletePlayer(player: Player)

    @Query("DELETE FROM Player")
    suspend fun deleteAllPlayer()

    @Query("DELETE FROM Player WHERE id = :id")
    fun deletePlayerById(id: Int)
}