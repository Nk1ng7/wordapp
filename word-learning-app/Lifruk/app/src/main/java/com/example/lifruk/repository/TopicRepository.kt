package com.example.lifruk.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.lifruk.data.Player
import com.example.lifruk.data.Topic
import com.example.lifruk.data.TopicDao

class TopicRepository(private val topicDao: TopicDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allTopics: LiveData<List<Topic>> = topicDao.getAllTopics()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertTopic(topic: Topic) {
        topicDao.insertTopic(topic)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getTopicById(id: Int) : LiveData<Topic> {
        return topicDao.getTopicById(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAllTopic() {
        topicDao.deleteAllTopic()
    }
}