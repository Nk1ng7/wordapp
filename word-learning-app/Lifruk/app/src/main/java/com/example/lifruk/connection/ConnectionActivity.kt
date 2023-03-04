package com.example.lifruk.connection

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.FragmentActivity
import com.example.lifruk.R
import java.util.*

class ConnectionActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_connection)
        //updating the language
        loadLocale()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.connection_content, SignUpFragment())
            .commit()
    }
    private fun loadLocale() {
        //getting value of saved language
        val sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE)
        val language = sharedPreferences.getString("app_lang", "")
        //checking if the value is empty
        if (language != null) {
            //when value is not empty setting the language which is selected
            setLocale(language)
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
    fun switchToSignUpFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.connection_content, SignUpFragment())
            .addToBackStack(null)
            .commit()
    }

    fun switchToLogInFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.connection_content, LogInFragment())
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return true
    }
}
