package com.example.lifruk.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.lifruk.data.Translation
import com.example.lifruk.repository.TranslationRepository
import kotlinx.coroutines.launch

class TranslationViewModel(private val repository: TranslationRepository) : ViewModel() {

    // Using LiveData and caching what allTranslations returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allTranslations: LiveData<List<Translation>> = repository.allTranslations

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insertTranslation(translation: Translation) = viewModelScope.launch {
        repository.insertTranslation(translation)
    }

    fun deleteAllTranslation() = viewModelScope.launch {
        repository.deleteAllTranslation()
    }
}

class TranslationViewModelFactory(private val repository: TranslationRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TranslationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TranslationViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
