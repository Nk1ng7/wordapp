package com.example.lifruk.game_menu

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.lifruk.App
import com.example.lifruk.R
import com.example.lifruk.data.*
import com.example.lifruk.flashcard.FlashcardActivity
import com.example.lifruk.information.PlayerActivity
import com.example.lifruk.quiz.QuizActivity
import com.example.lifruk.settings.SettingsActivity
import com.example.lifruk.view_model.TopicViewModel
import com.example.lifruk.view_model.TopicViewModelFactory
import com.example.lifruk.webserver.LifrukAPI
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import java.util.*


class GameMenuActivity : AppCompatActivity(), TopicListAdapter.TopicItemListener {

    companion object {
        const val EXTRA_GAME = "selected_game"
        const val EXTRA_TYPE = "selected_type"
        const val EXTRA_TOPIC = "topic"
        const val EXTRA_PLAYER = "player"
    }

    // Topic database
    private lateinit var topics: MutableList<Topic>

    lateinit var newTopic: Topic

    // ViewModel
    private val topicViewModel: TopicViewModel by viewModels {
        TopicViewModelFactory((application as App).topicRepository)
    }

    // Files
    private lateinit var topicAdapter: TopicListAdapter
    private lateinit var addTopicDialog: AddDialogFragment

    // View
    lateinit var coordinatorLayout: CoordinatorLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var button_addTopic: ExtendedFloatingActionButton
    private lateinit var tabLayout: TabLayout

    // var
    private var selected_game: GameMode = GameMode.QUIZ
    private var selected_type: Type = Type.WORD
    private var playerID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_game_menu)

        getViewById()
        getIntentExtra()
        selectTab()
        loadLocale()
        topics = mutableListOf()
        topicAdapter = TopicListAdapter(this)
        topicData()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@GameMenuActivity)
            adapter = topicAdapter
        }

        //swipeRefresh.setOnRefreshListener { viewModel.refreshTopics() }
        if (selected_game == GameMode.QUIZ) {
            button_addTopic.visibility = View.GONE
        }
        button_addTopic.visibility = View.GONE
        //button_addTopic.setOnClickListener { showAddTopicDialog() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        /*if (id == R.id.settings) {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        } else */
        if (id == R.id.menu_player) {
            val intent = Intent(this, PlayerActivity::class.java)
            startActivity(intent)
        } else if (id == R.id.menu_connection) {
            App.player = null
        } else if (id == R.id.settings) {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
        return true
    }

    private fun loadLocale() {
        val sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE)
        val language = sharedPreferences.getString("app_lang", "")
        if (language != null) {
            setLocale(language)
        }
    }

    private fun setLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration = Configuration()
        configuration.locale = locale
        baseContext.resources.updateConfiguration(
            configuration,
            baseContext.resources.displayMetrics
        )
        val editor = getSharedPreferences("Settings", MODE_PRIVATE).edit()
        editor.putString("app_lang", language)
        editor.apply()
    }
    private fun getViewById() {
        recyclerView = findViewById(R.id.recyclerView)
        swipeRefresh = findViewById(R.id.swipeRefresh)
        coordinatorLayout = findViewById(R.id.coordinator_layout)
        button_addTopic = findViewById(R.id.button_addTopic)
        tabLayout = findViewById(R.id.tabLayout)
    }

    private fun getIntentExtra() {
        selected_game = intent.getStringExtra(EXTRA_GAME)?.let { toMode(it) }!!
        playerID = intent.getIntExtra(EXTRA_PLAYER, 0)
    }

    private fun selectTab() {
        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> selected_type = Type.WORD
                    1 -> selected_type = Type.AUDIO
                    2 -> selected_type = Type.PICTURE
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun topicData() {
        val sharedPreferences = getSharedPreferences("Level", MODE_PRIVATE)
        val level = sharedPreferences.getString("level", "")
        if (level == getString(R.string.easy)) {
            topicAdapter.submitList(listOf(Topic(0, getString(R.string.color), Difficulty.EASY)))
        } else if (level == getString(R.string.medium)) {
            topicAdapter.submitList(listOf(Topic(0, getString(R.string.animal), Difficulty.MEDIUM)))
        } else if (level == getString(R.string.Difficult)) {
            topicAdapter.submitList(listOf(Topic(0, getString(R.string.food), Difficulty.HARD)))
        } else {
            topicAdapter.submitList(listOf(Topic(0, getString(R.string.color), Difficulty.EASY)))
        }/* topicViewModel.allTopics.observe(this, Observer { topics ->
             // Update the cached copy of the words in the adapter.
             topics?.let { topicAdapter.submitList(it) }
         })*/
    }

    private fun showAddTopicDialog() {
        addTopicDialog = AddDialogFragment()
        addTopicDialog.listener = object :
            AddDialogFragment.ConfirmAddDialogListener {
            override fun onDialogPositiveClick(inputTextTopicName: String) {
                addTopic(inputTextTopicName)
                dismissTopicDialog()
            }

            override fun onDialogNegativeClick() {
                dismissTopicDialog()
            }
        }

        addTopicDialog.show(supportFragmentManager, "confirmAddDialog")
    }

    private fun dismissTopicDialog() {
        addTopicDialog.dismiss()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateTopics(newTopics: List<Topic>) {
        //Timber.d("List of topics $newTopics")
        topics.clear()
        topics.addAll(newTopics)
        topicAdapter.notifyDataSetChanged()
        swipeRefresh.isRefreshing = false
    }

    override fun onTopicSelected(topic: Topic) {
        val intent = configIntent(topic)
        startActivity(intent)
    }

    private fun configIntent(topic: Topic): Intent? {
        intent = when (selected_game) {
            GameMode.FLASHCARD -> Intent(this, FlashcardActivity::class.java)
            GameMode.QUIZ -> Intent(this, QuizActivity::class.java)
        }
        intent.putExtra(EXTRA_TYPE, fromType(selected_type))
        intent.putExtra(EXTRA_TOPIC, topic.id)
        intent.putExtra(EXTRA_PLAYER, playerID)
        return intent
    }

    private fun addTopic(inputTopicName: String) {
        newTopic.name = inputTopicName
        newTopic.difficulty = Difficulty.EASY
        //newTopic.mode = selected_game
        //newTopic.type = selected_type
        //topicViewModel.insertTopic(newTopic)
    }

    private fun getTopics() {
        this.applicationContext?.let {
            LifrukAPI.getInstance(it).getAllTopics(
                success = { topicsList ->
                    // do something with the topics
                    topics = topicsList.toMutableList()
                },
                failure = { message ->
                    // handle the error
                }
            )
        }


        /*topicViewModel.allTopics.observe(this, Observer { topics ->
            // Update the cached copy of the words in the adapter.
            topics?.let { topicAdapter.submitList(it) }
        })*/
    }

}
