package com.example.lifruk.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.lifruk.data.Player
import com.example.lifruk.data.Topic
import com.example.lifruk.repository.TopicRepository
import kotlinx.coroutines.launch

class TopicViewModel(private val repository: TopicRepository) : ViewModel() {

    // Using LiveData and caching what allTopics returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allTopics: LiveData<List<Topic>> = repository.allTopics

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insertTopic(topic: Topic) = viewModelScope.launch {
        repository.insertTopic(topic)
    }

    fun getTopicById(id: Int) : LiveData<Topic> {
        lateinit var topic: LiveData<Topic>
        viewModelScope.launch {
            topic = repository.getTopicById(id)
        }
        return topic
    }

    fun deleteAllTopic() = viewModelScope.launch {
        repository.deleteAllTopic()
    }
}

class TopicViewModelFactory(private val repository: TopicRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TopicViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TopicViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
