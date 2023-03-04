package com.example.lifruk
import com.example.lifruk.connection.SignUpFragment
import org.junit.Assert
import org.junit.Test

class SignUpFragmentTest {
    @Test
    fun testIsFormValidWithIncorrectData(){
        val signUp = SignUpFragment()
        signUp.usernameValue = "qwerty"
        signUp.emailValue = "test@mail.com"
        signUp.passwordValue = "12345678"
        signUp.passwordConfirmationValue = "12345678"
        signUp.nativeLanguageValue = "English"
        signUp.learningLanguageValue = "English"
        Assert.assertTrue(!signUp.isFormValid())
    }
    @Test
    fun testIsFormValidWithCorrectData(){
        val signUp = SignUpFragment()
        signUp.usernameValue = "qwerty"
        signUp.emailValue = "test@mail.com"
        signUp.passwordValue = "12345678"
        signUp.passwordConfirmationValue = "12345678"
        signUp.nativeLanguageValue = "English"
        signUp.learningLanguageValue = "Lithuanian"
        Assert.assertTrue(signUp.isFormValid())
    }
}