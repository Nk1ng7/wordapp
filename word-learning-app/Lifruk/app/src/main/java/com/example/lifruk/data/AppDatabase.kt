package com.example.lifruk.data

import android.content.Context
import android.util.Log
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

const val DATABASE_NAME = "lifruk_database"

@Database(entities = [Answer::class, Flashcard::class, Game::class, Player::class, Topic::class, Translation::class, Word::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun answerDao() : AnswerDao
    abstract fun flashcardDao() : FlashcardDao
    abstract fun gameDao() : GameDao
    abstract fun playerDao() : PlayerDao
    abstract fun topicDao() : TopicDao
    abstract fun translationDao() : TranslationDao
    abstract fun wordDao() : WordDao

    private class AppDatabaseCallback (
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        //private lateinit var generator: Generator

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            INSTANCE?.let { database ->
                scope.launch {
                    //generateTopics(database.topicDao())
                    //generateWord(database.wordDao())
                    //generateTranslation(database.translationDao())
                    //generatePlayer(database.playerDao())
                }
            }
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context,
                        scope: CoroutineScope
        ): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    AppDatabase::class.java.name
                )
                    .addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}