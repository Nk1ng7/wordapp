package com.example.lifruk.connection

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.lifruk.App
import com.example.lifruk.R
import com.example.lifruk.data.*
import com.example.lifruk.toolbox.hashPassword
import com.example.lifruk.webserver.LifrukAPI
import com.google.android.material.textfield.TextInputEditText
import isEmailWellFormatted


class SignUpFragment : Fragment() {
    private lateinit var signupValidate: Button
    private lateinit var signupGoLogin: Button
    private lateinit var signUpUsername: TextInputEditText
    private lateinit var signUpEmail: TextInputEditText
    private lateinit var signUpPwd: TextInputEditText
    private lateinit var signUpConfPwd: TextInputEditText
    private lateinit var nativeSpinner: Spinner
    private lateinit var learningSpinner: Spinner

    internal lateinit var usernameValue: String
    internal lateinit var emailValue: String
    internal lateinit var passwordValue: String
    internal lateinit var passwordConfirmationValue: String
    internal lateinit var nativeLanguageValue: String
    internal lateinit var learningLanguageValue: String

    private lateinit var toastErrMsg: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(com.example.lifruk.R.layout.activity_sign_up, container, false)

        getViewById(view)

        signupGoLogin.setOnClickListener {
            // Switch to the sign up fragment
            (activity as ConnectionActivity).switchToLogInFragment()
        }
        signupValidate.setOnClickListener {
            getInput()
            // Validate the form and create a new account if the form is valid
            if (isFormValid()) {
                createAccount()
            } else {
                this.context?.let {
                    Toast.makeText(it, toastErrMsg, Toast.LENGTH_SHORT).show()
                }
            }
        }

        return view
    }

    private fun fillupSpinner(view: View, spinner_id: Int, list_id: Int): Spinner {
        // Find the Spinner object
        val spinner: Spinner = view.findViewById(spinner_id)

        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = this.context?.let {
            ArrayAdapter.createFromResource(
                it, list_id, android.R.layout.simple_spinner_item
            )
        }

        // Specify the layout to use when the list of choices appears
        adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the spinner
        spinner.adapter = adapter

        return spinner
    }

    private fun getViewById(view: View) {
        signupValidate = view.findViewById(R.id.signup_validate)
        signupGoLogin = view.findViewById(R.id.signup_go_login)
        signUpUsername = view.findViewById(R.id.signup_username)
        signUpEmail = view.findViewById(R.id.signup_email)
        signUpPwd = view.findViewById(R.id.signup_pwd)
        signUpConfPwd = view.findViewById(R.id.signup_conf_pwd)

        nativeSpinner = fillupSpinner(
            view, R.id.signin_listLanguageN, R.array.listL
        )
        learningSpinner = fillupSpinner(
            view, R.id.signin_listLanguageL, R.array.listL
        )
    }

    private fun getInput() {
        usernameValue = signUpUsername.text.toString().replace(Regex("\\s"), "")
        emailValue = signUpEmail.text.toString().replace(Regex("\\s"), "")
        passwordValue = signUpPwd.text.toString().replace(Regex("\\s"), "")
        passwordConfirmationValue = signUpConfPwd.text.toString().replace(Regex("\\s"), "")
        nativeLanguageValue = nativeSpinner.selectedItem.toString().replace(Regex("\\s"), "")
        learningLanguageValue = learningSpinner.selectedItem.toString().replace(Regex("\\s"), "")
    }

    private fun isUsernameFieldValid(): Boolean {
        if (usernameValue == "") {
            toastErrMsg = getString(R.string.errUsernameIsEmpty)
            return false
        }
        return true
    }

    private fun isEmailFieldValid(): Boolean {
        if (!isEmailWellFormatted(emailValue)) {
            toastErrMsg = getString(R.string.errEmailInvalidFormat)
            return false
        }
        return true
    }

    private fun arePasswordFieldsValid(): Boolean {
        if (passwordValue == "" || passwordConfirmationValue == "" || passwordValue != passwordConfirmationValue) {
            toastErrMsg = getString(R.string.errPasswordDontMatch)
            return false
        }
        return true
    }

    private fun areLanguagesFieldsValid(): Boolean {
        if (nativeLanguageValue == learningLanguageValue) {
            toastErrMsg = getString(R.string.errLanguageSelection)
            return false
        }
        return true
    }

    internal fun isFormValid(): Boolean {

        return (isUsernameFieldValid() && isEmailFieldValid() && arePasswordFieldsValid() && areLanguagesFieldsValid())
    }

    private fun createAccount() {
        val player = Player(
            0,
            usernameValue,
            emailValue,
            hashPassword(passwordValue),
            Language.fromString(nativeLanguageValue),
            Language.fromString(learningLanguageValue),
            Difficulty.EASY
        )

        this.context?.let {
            LifrukAPI.getInstance(it).registerPlayer(player, { response ->
                // handle the response
                Toast.makeText(
                    it, "Player ${response.get("username")} just created !", Toast.LENGTH_SHORT
                ).show()
                App.player = Player(
                    response.getInt("id"),
                    response.getString("username"),
                    response.getString("email"),
                    response.getString("pwd"),
                    toLanguage(response.getString("native_l")),
                    toLanguage(response.getString("learning_l")),
                    toDifficulty(response.getString("dlevel")),
                )
                Log.i("obsi/Registered", App.player!!.asJSON().toString())
                navigateBack()
            }, { error ->
                // handle the error
                error.message?.let { msg -> Log.e("obsi/SignUp", msg) }
                Toast.makeText(it, getString(R.string.errSignupFailed), Toast.LENGTH_SHORT).show()
            })
        }
    }

    private fun navigateBack() {
        activity.let {
            it?.finish()
        }
    }
}