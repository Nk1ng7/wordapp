package com.example.lifruk.information

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lifruk.App
import com.example.lifruk.R
import com.example.lifruk.connection.ConnectionActivity
import com.example.lifruk.data.fromLanguage
import java.util.*

class PlayerActivity : AppCompatActivity() {
    private lateinit var playerUsernameView: TextView
    private lateinit var playerNativeLView: TextView
    private lateinit var playerLearningLView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        playerUsernameView = this.findViewById<TextView>(R.id.player_username)
        playerNativeLView = this.findViewById<TextView>(R.id.player_native_language)
        playerLearningLView = this.findViewById<TextView>(R.id.player_language_to_learn)
    }

    override fun onResume() {
        super.onResume()

        if (App.player == null) {
            Toast.makeText(
                this, "You must login first!", Toast.LENGTH_SHORT
            ).show()
            navigateToAuth()
        } else {
            playerUsernameView.text = App.player!!.username
            playerNativeLView.text = fromLanguage(App.player!!.native_l).lowercase()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            playerLearningLView.text = fromLanguage(App.player!!.learning_l).lowercase()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        }
    }

    private fun navigateToAuth() {
        val intent = Intent(this, ConnectionActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }
}