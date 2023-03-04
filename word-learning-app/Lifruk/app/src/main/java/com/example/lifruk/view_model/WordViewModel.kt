package com.example.lifruk.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.lifruk.data.Language
import com.example.lifruk.data.Word
import com.example.lifruk.repository.WordRepository
import kotlinx.coroutines.launch

class WordViewModel(private val repository: WordRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<Word>> = repository.allWords

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insertWord(word: Word) = viewModelScope.launch {
        repository.insertWord(word)
    }

    fun getWordById(wordID: Int) : Word {
        lateinit var word: Word
        viewModelScope.launch {
            word = repository.getWordById(wordID)
        }
        return word
    }

    fun getRandomQuestions(nb: Int, topicID: Int, language: Language) : List<Word> {
        var wordList: List<Word> = listOf()
        viewModelScope.launch {
            wordList = repository.getRandomQuestions(nb, topicID, language)

            Log.i("-----------", "**********************************************************")
            //println(repository.getRandomQuestions(nb, topicID, language)[0].content)
            Log.i("-----------", "**********************************************************")
        }
        return wordList
    }

    fun getErrorQuestions(playerId: Int, topicID: Int, language: Language) : List<Word>  {
        var wordList: List<Word> = listOf()
        viewModelScope.launch {
            wordList = repository.getErrorQuestions(playerId, topicID, language)
        }
        return wordList
    }

    fun random3OthersWords(wordID: Int, topicID: Int, language: Language) : List<Word> {
        var wordList: List<Word> = listOf()

        viewModelScope.launch {
            wordList = repository.random3OthersPictures(wordID, topicID, language)
        }
        return wordList
    }

    /*fun getRandomQuestions(nb: Int, topicID: Int, language: Language) : List<Word> {
        return repository.getRandomQuestions(nb, topicID, language)
    }

    fun getErrorQuestions(playerId: Int, topicID: Int, language: Language) : List<Word>  {
        return repository.getErrorQuestions(playerId, topicID, language)
    }

    fun random3OthersWords(wordID: Int, topicID: Int, language: Language) : List<Word> {
        return repository.random3OthersPictures(wordID, topicID, language)
    }*/

    fun deleteAllWord() = viewModelScope.launch {
        repository.deleteAllWord()
    }
}

class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}