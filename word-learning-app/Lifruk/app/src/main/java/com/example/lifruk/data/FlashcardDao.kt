package com.example.lifruk.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FlashcardDao {
    @Query("SELECT * FROM Flashcard")
    fun getAllFlashcards() : LiveData<List<Flashcard>>

    @Query("SELECT * FROM Flashcard WHERE id = :id")
    fun getFlashcardById(id: Int) : LiveData<Flashcard>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFlashcard(flashcard: Flashcard)

    @Delete
    fun deleteFlashcard(flashcard: Flashcard)

    @Query("DELETE FROM Flashcard")
    suspend fun deleteAllFlashcard()

    @Query("DELETE FROM Flashcard WHERE id = :id")
    fun deleteFlashcardById(id: Int)
}