package com.example.lifruk.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WordDao {

    @Query("SELECT * FROM Word")
    fun getAllWords() : LiveData<List<Word>>

    @Query("SELECT * FROM Word WHERE id = :id")
    fun getWordById(id: Int) : Word

    @Query("SELECT * FROM Word " +
            "WHERE Word.topic = :topicID " +
            "AND Word.language = :language " +
            "ORDER BY RANDOM() LIMIT :nb")
    suspend fun getRandomQuestions(nb: Int, topicID: Int, language: Language) : List<Word>

    @Query("SELECT Word.* FROM Player, Answer, Game, Word " +
            "WHERE Player.id = Game.player  " +
            "AND Game.id = Answer.game " +
            "AND Answer.word = Word.id " +
            "AND Word.language = :language " +
            "AND Word.topic = :topicID " +
            "AND Answer.correct = 0 " +
            "AND Player.id = :idPlayer ORDER BY RANDOM() LIMIT 5")
    suspend fun getErrorQuestions(idPlayer: Int, topicID: Int, language: Language) : List<Word>

    @Query("SELECT * FROM Word WHERE " +
            "Word.topic = :topicID " +
            "AND Word.language = :language " +
            "AND Word.id NOT IN (:wordID) " +
            "LIMIT 3")
    suspend fun random3OthersWords(wordID: Int, topicID: Int, language: Language) : List<Word>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWord(word: Word)

    @Delete
    fun deleteWord(word: Word)

    @Query("DELETE FROM Word")
    suspend fun deleteAllWord()

    @Query("DELETE FROM Word WHERE id = :id")
    fun deleteWordById(id: Int)
}