package com.example.lifruk.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TranslationDao {

    @Query("SELECT * FROM Translation")
    fun getAllTranslations() : LiveData<List<Translation>>

    @Query("SELECT * FROM Translation WHERE id = :id")
    fun getTranslationById(id: Int) : LiveData<Translation>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTranslation(translation: Translation)

    @Delete
    fun deleteTranslation(translation: Translation)

    @Query("DELETE FROM Translation")
    suspend fun deleteAllTranslation()

    @Query("DELETE FROM Translation WHERE id = :id")
    fun deleteTranslationById(id: Int)
}