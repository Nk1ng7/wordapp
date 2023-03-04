package com.example.lifruk.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.lifruk.data.Player
import com.example.lifruk.data.PlayerDao

class PlayerRepository(private val playerDao: PlayerDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allPlayers: LiveData<List<Player>> = playerDao.getAllPlayers()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertPlayer(player: Player) {
        playerDao.insertPlayer(player)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getPlayerById(id: Int) : LiveData<Player> {
        return playerDao.getPlayerById(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getPlayerByEmailAndPwd(email: String, pwd: String) : LiveData<Player> {
        return playerDao.getPlayerByEmailAndPwd(email, pwd)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAllPlayer() {
        playerDao.deleteAllPlayer()
    }
}