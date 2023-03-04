package com.example.lifruk

import android.app.Application
import com.example.lifruk.data.AppDatabase
import com.example.lifruk.data.Player
import com.example.lifruk.repository.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class App : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    private val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    private val database by lazy { AppDatabase.getDatabase(this, applicationScope) }

    val answerRepository by lazy { AnswerRepository(database.answerDao()) }
    val flashcardRepository by lazy { FlashcardRepository(database.flashcardDao()) }
    val gameRepository by lazy { GameRepository(database.gameDao()) }
    val playerRepository by lazy { PlayerRepository(database.playerDao()) }
    val topicRepository by lazy { TopicRepository(database.topicDao()) }
    val translationRepository by lazy { TranslationRepository(database.translationDao()) }
    val wordRepository by lazy { WordRepository(database.wordDao()) }

    companion object {
        var player: Player? = null
    }
}
