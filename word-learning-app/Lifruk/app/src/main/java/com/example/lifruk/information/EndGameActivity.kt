package com.example.lifruk.information

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import com.example.lifruk.App
import com.example.lifruk.MainActivity
import com.example.lifruk.R
import com.example.lifruk.quiz.QuizActivity

class EndGameActivity : AppCompatActivity() {

    private var score: Int = 0
    private lateinit var scoreText: TextView
    private lateinit var scoreRatingBar: RatingBar
    private lateinit var username: TextView
    private lateinit var scoreMenu: Button

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_game)

        getIntentExtra()
        getViewById()

        scoreText.text = "Your score: $score/5"
        scoreRatingBar.rating = (score).toFloat()
        username.text = App.player?.username ?: ""

        scoreMenu.setOnClickListener { goMenu() }
    }

    private fun getViewById() {
        scoreText = findViewById(R.id.score_text)
        scoreRatingBar = findViewById(R.id.score_ratingBar)
        username = findViewById(R.id.score_username)
        scoreMenu = findViewById(R.id.score_menu)
    }

    private fun getIntentExtra() {
        score = intent.getIntExtra(QuizActivity.EXTRA_SCORE, 0)
    }


    private fun goMenu() {
        intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


}