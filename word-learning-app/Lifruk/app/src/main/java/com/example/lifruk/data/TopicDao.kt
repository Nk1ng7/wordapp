package com.example.lifruk.data

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TopicDao {

    @Query("SELECT * FROM Topic")
    fun getAllTopics() : LiveData<List<Topic>>

    @Query("SELECT * FROM Topic WHERE id = :id")
    fun getTopicById(id: Int) : LiveData<Topic>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTopic(topic: Topic)

    @Delete
    fun deleteTopic(topic: Topic)

    @Query("DELETE FROM Topic")
    suspend fun deleteAllTopic()

    @Query("DELETE FROM Topic WHERE id = :id")
    fun deleteTopicById(id: Int)
}