package com.example.lifruk.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GameDao {
    @Query("SELECT * FROM Game")
    fun getAllGames() : LiveData<List<Game>>

    @Query("SELECT * FROM Game WHERE id = :id")
    fun getGameById(id: Int) : LiveData<Game>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGame(game: Game)

    @Delete
    fun deleteGame(game: Game)

    @Query("DELETE FROM Game")
    suspend fun deleteAllGame()

    @Query("DELETE FROM Game WHERE id = :id")
    fun deleteGameById(id: Int)
}