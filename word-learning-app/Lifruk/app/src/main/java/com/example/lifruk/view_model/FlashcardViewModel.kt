package com.example.lifruk.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.lifruk.data.Flashcard
import com.example.lifruk.repository.FlashcardRepository
import kotlinx.coroutines.launch

class FlashcardViewModel(private val repository: FlashcardRepository) : ViewModel() {

    // Using LiveData and caching what allFlashcards returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allFlashcards: LiveData<List<Flashcard>> = repository.allFlashcards

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insertFlashcard(flashcard: Flashcard) = viewModelScope.launch {
        repository.insertFlashcard(flashcard)
    }

    fun deleteAllFlashcard() = viewModelScope.launch {
        repository.deleteAllFlashcard()
    }
}

class FlashcardViewModelFactory(private val repository: FlashcardRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlashcardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FlashcardViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}