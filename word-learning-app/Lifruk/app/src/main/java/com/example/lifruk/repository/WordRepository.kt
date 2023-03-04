package com.example.lifruk.repository


import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.lifruk.data.Language
import com.example.lifruk.data.Word
import com.example.lifruk.data.WordDao
import com.example.lifruk.data.fromLanguage

class WordRepository(private val wordDao: WordDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allWords: LiveData<List<Word>> = wordDao.getAllWords()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertWord(word: Word) {
        wordDao.insertWord(word)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAllWord() {
        wordDao.deleteAllWord()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getWordById(wordID: Int) : Word {
        return wordDao.getWordById(wordID)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getRandomQuestions(nb: Int, topicID: Int, language: Language) : List<Word> {
        return wordDao.getRandomQuestions(nb, topicID, language)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getErrorQuestions(playerId: Int, topicID: Int, language: Language) : List<Word> {
        return wordDao.getErrorQuestions(playerId, topicID, language)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun random3OthersPictures(wordID: Int, topicID: Int, language: Language) : List<Word> {
        return wordDao.random3OthersWords(wordID, topicID, language)
    }

}