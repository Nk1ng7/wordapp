package com.example.lifruk.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.lifruk.data.Player
import com.example.lifruk.data.Topic
import com.example.lifruk.repository.PlayerRepository
import kotlinx.coroutines.launch

class PlayerViewModel(private val repository: PlayerRepository) : ViewModel() {

    // Using LiveData and caching what allPlayers returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allPlayers: LiveData<List<Player>> = repository.allPlayers

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insertPlayer(player: Player) = viewModelScope.launch {
        repository.insertPlayer(player)
    }

    fun getPlayerById(id: Int) : LiveData<Player> {
        lateinit var player: LiveData<Player>
        viewModelScope.launch {
            player = repository.getPlayerById(id)
        }
        return player
    }

    fun getPlayerByEmailAndPwd(email: String, pwd: String) : LiveData<Player> {
        lateinit var player: LiveData<Player>
        viewModelScope.launch {
            player = repository.getPlayerByEmailAndPwd(email, pwd)
        }
        return player
    }

    fun deleteAllPlayer() = viewModelScope.launch {
        repository.deleteAllPlayer()
    }
}

class PlayerViewModelFactory(private val repository: PlayerRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlayerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlayerViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}