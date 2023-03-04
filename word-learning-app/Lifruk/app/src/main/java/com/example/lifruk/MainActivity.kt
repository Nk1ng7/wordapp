package com.example.lifruk

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.lifruk.connection.ConnectionActivity
import com.example.lifruk.data.GameMode
import com.example.lifruk.data.fromMode
import com.example.lifruk.game_menu.GameMenuActivity
import com.example.lifruk.information.PlayerActivity
import com.example.lifruk.settings.SettingsActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var buttonFlashcard: Button
    private lateinit var buttonQuiz: Button
    private lateinit var buttonConnection: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_menu)
        setLevel()
        loadLocale()
        buttonFlashcard = findViewById(R.id.button_flashcard)
        buttonFlashcard.text=getString(R.string.flash_card)
        buttonFlashcard.setOnClickListener { selectGame(fromMode(GameMode.FLASHCARD)) }

        buttonQuiz = findViewById(R.id.button_quiz)
        buttonQuiz.text=getString(R.string.quiz)
        buttonQuiz.setOnClickListener { selectGame(fromMode(GameMode.QUIZ)) }



        buttonConnection = findViewById(R.id.button_connection)
        buttonConnection.text=getString(R.string.connection)
        buttonConnection.setOnClickListener { selectConnection() }
        if (App.player == null) {
            buttonQuiz.visibility = View.INVISIBLE
            buttonFlashcard.visibility = View.INVISIBLE
        } else {
            buttonQuiz.visibility = View.VISIBLE
            buttonFlashcard.visibility = View.VISIBLE
        }
    }

    private fun setLevel(){
        val sharedPreferences = getSharedPreferences("Level", MODE_PRIVATE)
        val level = sharedPreferences.getString("level",null)
        if (level==null) {
            val editor = getSharedPreferences("Level", MODE_PRIVATE).edit()
            editor.putString("level",getString(R.string.easy))
            editor.apply()
        }
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

    override fun onResume() {
        super.onResume()
        loadLocale()
        if (App.player == null) {
            buttonQuiz.visibility = View.INVISIBLE
            buttonFlashcard.visibility = View.INVISIBLE
        } else {
            buttonQuiz.visibility = View.VISIBLE
            buttonFlashcard.visibility = View.VISIBLE
        }
    }

    private fun selectGame(game: String) {
        val intent = Intent(this, GameMenuActivity::class.java)
        intent.putExtra(GameMenuActivity.EXTRA_GAME, game)
        intent.putExtra(GameMenuActivity.EXTRA_PLAYER, 0)
        startActivity(intent)
    }

    private fun selectConnection() {
        val intent = Intent(this, ConnectionActivity::class.java)
        startActivity(intent)
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

}