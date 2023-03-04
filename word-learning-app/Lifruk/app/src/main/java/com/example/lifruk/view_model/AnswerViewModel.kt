package com.example.lifruk.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.lifruk.data.Answer
import com.example.lifruk.repository.AnswerRepository
import kotlinx.coroutines.launch

class AnswerViewModel(private val repository: AnswerRepository) : ViewModel() {

    // Using LiveData and caching what allAnswers returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allAnswers: LiveData<List<Answer>> = repository.allAnswers

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insertAnswer(answer: Answer) = viewModelScope.launch {
        repository.insertAnswer(answer)
    }

    fun deleteAllAnswer() = viewModelScope.launch {
        repository.deleteAllAnswer()
    }
}

class AnswerViewModelFactory(private val repository: AnswerRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnswerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AnswerViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}