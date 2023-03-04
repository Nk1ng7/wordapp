package com.example.lifruk.connection

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.lifruk.App
import com.example.lifruk.R
import com.example.lifruk.data.*
import com.example.lifruk.toolbox.hashPassword
import com.example.lifruk.webserver.LifrukAPI
import com.google.android.material.textfield.TextInputEditText
import isEmailWellFormatted

class LogInFragment : Fragment() {

    private lateinit var loginValidate: Button
    private lateinit var loginGoSignIn: Button
    private lateinit var loginEmail: TextInputEditText
    private lateinit var loginPwd: TextInputEditText
    private lateinit var loginError: TextView

    private lateinit var inputEmail: String
    private lateinit var inputPwd: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.activity_log_in, container, false)
        getViewById(view)

        if (App.player != null) this.context?.let {
            Toast.makeText(it, "Already connected as ${App.player!!.username}!", Toast.LENGTH_SHORT)
                .show()
            navigateBack()
        }

        loginGoSignIn.setOnClickListener {
            // Switch to the sign up fragment
            (activity as ConnectionActivity).switchToSignUpFragment()
        }

        loginValidate.setOnClickListener {
            if (isFormValid()) {
                login()
            } else {
                this.context?.let {
                    Toast.makeText(it, toastErrMsg, Toast.LENGTH_SHORT).show()
                }
            }
        }

        return view
    }

    private lateinit var toastErrMsg: String

    private fun getViewById(view: View) {
        loginValidate = view.findViewById(R.id.login_validate)
        loginGoSignIn = view.findViewById(R.id.login_go_signin)
        loginEmail = view.findViewById(R.id.login_email)
        loginPwd = view.findViewById(R.id.login_pwd)
        loginError = view.findViewById(R.id.login_error)
    }

    private fun getInput() {
        inputEmail = loginEmail.text.toString().replace(Regex("\\s"), "")
        inputPwd = loginPwd.text.toString().replace(Regex("\\s"), "")
    }

    private fun isEmailFieldValid(): Boolean {
        if (!isEmailWellFormatted(inputEmail)) {
            toastErrMsg = getString(R.string.errEmailInvalidFormat)
            return false
        }
        return true
    }

    private fun isPasswordFieldNotEmpty(): Boolean {
        if (inputPwd == "") {
            toastErrMsg = getString(R.string.errPasswordEmpty)
            return false
        }
        return true
    }

    private fun isFormValid(): Boolean {
        getInput()
        return (isEmailFieldValid() && isPasswordFieldNotEmpty())
    }

    private fun login() {
        this.context?.let {
            LifrukAPI.getInstance(it).logPlayerIn(inputEmail, hashPassword(inputPwd), { response ->
                // handle the response
                Toast.makeText(
                    it, "Welcome ${response.get("username")}!", Toast.LENGTH_SHORT
                ).show()
                App.player = Player(
                    response.getInt("id"),
                    response.getString("username"),
                    response.getString("email"),
                    response.getString("pwd"),
                    toLanguage(response.getString("native_l")),
                    toLanguage(response.getString("learning_l")),
                    toDifficulty(response.getString("dlevel"))
                )
                Log.i("obsi/LoggedIn", App.player!!.asJSON().toString())
                navigateBack()
            }, { error ->
                // handle the error
                error.message?.let { msg -> Log.e("obsi/LogIn", msg) }
                Toast.makeText(it, getString(R.string.errLoginFailed), Toast.LENGTH_SHORT).show()
            })
        }
    }

    private fun navigateBack() {
        activity.let {
            it?.finish()
        }
    }
}