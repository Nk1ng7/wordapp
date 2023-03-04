package com.example.lifruk.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.lifruk.data.Translation
import com.example.lifruk.data.TranslationDao

class TranslationRepository(private val translationDao: TranslationDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allTranslations: LiveData<List<Translation>> = translationDao.getAllTranslations()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertTranslation(translation: Translation) {
        translationDao.insertTranslation(translation)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAllTranslation() {
        translationDao.deleteAllTranslation()
    }
}