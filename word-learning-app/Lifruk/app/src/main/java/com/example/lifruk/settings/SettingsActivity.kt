package com.example.lifruk.settings

import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.lifruk.App
import com.example.lifruk.MainActivity
import com.example.lifruk.R
import com.example.lifruk.connection.ConnectionActivity
import com.example.lifruk.data.Language
import com.example.lifruk.data.fromLanguage
import com.example.lifruk.data.toLanguage
import com.example.lifruk.webserver.LifrukAPI
import java.util.*


class SettingsActivity : AppCompatActivity() {

    private lateinit var languageBtn: Button
    private lateinit var levelBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)
        languageBtn = findViewById(R.id.language)
        levelBtn = findViewById(R.id.level)
        clickListener()
    }

    fun clickListener() {
        languageBtn.setOnClickListener {

            //creating dialog to show when language change button is clicked
            val builder = AlertDialog.Builder(this)
            val arrayAdapter =
                ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice)
            //setting choices for dialog through array Adapter
            arrayAdapter.add(getString(R.string.english))
            arrayAdapter.add(getString(R.string.french))
            arrayAdapter.add(getString(R.string.lithaunian))
            arrayAdapter.add(getString(R.string.ukrainian))
            builder.setTitle("Choose Language")
            builder.setSingleChoiceItems(
                arrayAdapter,
                -1,
                DialogInterface.OnClickListener { dialogInterface, i ->
                    //condition to perform operation based on user selection
                    when (i) {
                        0 -> {
                            //setting language
                            setLocale("")

                            //refreshing the app
                            val intent = Intent(this, MainActivity::class.java)
                            this.startActivity(intent)
                            finishAffinity()
                        }
                        1 -> {
                            //setting language
                            setLocale("fr")
                            //refreshing the app
                            val intent = Intent(this, MainActivity::class.java)
                            this.startActivity(intent)
                            finishAffinity()
                        }
                        2 -> {
                            //setting language
                            setLocale("lt")
                            //refreshing the app
                            val intent = Intent(this, MainActivity::class.java)
                            this.startActivity(intent)
                            finishAffinity()
                        }
                        3 -> {
                            //setting language
                            setLocale("uk")
                            //refreshing the app
                            val intent = Intent(this, MainActivity::class.java)
                            this.startActivity(intent)
                            finishAffinity()
                        }
                    }
                })
            builder.create()
            builder.show()
        }
        levelBtn.setOnClickListener {
            //creating dialog to show when level change button is clicked
            val builder = AlertDialog.Builder(this)
            val levelAdapter =
                ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice)
            //setting choices for level through array adapter
            levelAdapter.add(getString(R.string.easy))
            levelAdapter.add(getString(R.string.medium))
            levelAdapter.add(getString(R.string.Difficult))
            builder.setTitle("Choose Level")
            builder.setSingleChoiceItems(
                levelAdapter,
                -1,
                DialogInterface.OnClickListener { dialogInterface, i ->
                    when (i) {
                        0 -> {
                            //setting the level based on user selection in local database ( shared preferences)
                            val editor = getSharedPreferences("Level", MODE_PRIVATE).edit()
                            editor.putString("level",getString(R.string.easy))
                            editor.apply()
                            //refreshing the app
                            val intent = Intent(this, MainActivity::class.java)
                            this.startActivity(intent)
                            finishAffinity()
                        }
                        1 -> {
                            //setting the level based on user selection in local database ( shared preferences)
                            val editor = getSharedPreferences("Level", MODE_PRIVATE).edit()
                            editor.putString("level",getString(R.string.medium))
                            editor.apply()
                            //refreshing the app
                            val intent = Intent(this, MainActivity::class.java)
                            this.startActivity(intent)
                            finishAffinity()
                        }
                        2 -> {
                            //setting the level based on user selection in local database ( shared preferences)
                            val editor = getSharedPreferences("Level", MODE_PRIVATE).edit()
                            editor.putString("level",getString(R.string.Difficult))
                            editor.apply()
                            //refreshing the app
                            val intent = Intent(this, MainActivity::class.java)
                            this.startActivity(intent)
                            finishAffinity()
                        }
                    }
                })
            builder.create()
            builder.show()
        }

    }

    private fun setLocale(language: String) {
        //setting language through Locale
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

        if (App.player == null) {
            Toast.makeText(
                this, "You must login first!", Toast.LENGTH_SHORT
            ).show()
            navigateToAuth()
        } else {
            // updateLanguage()
        }
    }

    private fun updateLanguage() {
        LifrukAPI.getInstance(this)
            .updatePlayerLanguage(App.player!!.id, fromLanguage(Language.LITHUANIAN), { response ->
                // handle the response
                Toast.makeText(
                    this,
                    "${App.player!!.username} learning_l is now ${response.getString("learning_l")}!",
                    Toast.LENGTH_SHORT
                ).show()
                App.player!!.learning_l = toLanguage(response.getString("learning_l"))
            }, { error ->
                // handle the error
                error.message?.let { msg -> Log.e("obsi/updateLanguage", msg) }
            })
    }

    private fun navigateToAuth() {
        val intent = Intent(this, ConnectionActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }
}

