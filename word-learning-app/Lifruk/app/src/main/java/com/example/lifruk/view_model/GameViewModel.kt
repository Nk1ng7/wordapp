package com.example.lifruk.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.lifruk.data.Game
import com.example.lifruk.repository.GameRepository
import kotlinx.coroutines.launch

class GameViewModel(private val repository: GameRepository) : ViewModel() {

    // Using LiveData and caching what allGames returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allGames: LiveData<List<Game>> = repository.allGames

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insertGame(game: Game) = viewModelScope.launch {
        repository.insertGame(game)
    }

    fun deleteAllGame() = viewModelScope.launch {
        repository.deleteAllGame()
    }
}

class GameViewModelFactory(private val repository: GameRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GameViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}