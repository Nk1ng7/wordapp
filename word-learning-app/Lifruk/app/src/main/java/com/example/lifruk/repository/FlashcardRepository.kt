package com.example.lifruk.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.lifruk.data.Flashcard
import com.example.lifruk.data.FlashcardDao

class FlashcardRepository(private val flashcardDao: FlashcardDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allFlashcards: LiveData<List<Flashcard>> = flashcardDao.getAllFlashcards()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertFlashcard(flashcard: Flashcard) {
        flashcardDao.insertFlashcard(flashcard)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAllFlashcard() {
        flashcardDao.deleteAllFlashcard()
    }
}