package com.example.lifruk.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.lifruk.data.Answer
import com.example.lifruk.data.AnswerDao

class AnswerRepository(private val answerDao: AnswerDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allAnswers: LiveData<List<Answer>> = answerDao.getAllAnswers()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertAnswer(answer: Answer) {
        answerDao.insertAnswer(answer)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAllAnswer() {
        answerDao.deleteAllAnswer()
    }
}