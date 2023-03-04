package com.example.lifruk.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AnswerDao {
    @Query("SELECT * FROM Answer")
    fun getAllAnswers() : LiveData<List<Answer>>

    @Query("SELECT * FROM Answer WHERE id = :id")
    fun getAnswerById(id: Int) : LiveData<Answer>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAnswer(answer: Answer)

    @Delete
    fun deleteAnswer(answer: Answer)

    @Query("DELETE FROM Answer")
    suspend fun deleteAllAnswer()

    @Query("DELETE FROM Answer WHERE id = :id")
    fun deleteAnswerById(id: Int)
}