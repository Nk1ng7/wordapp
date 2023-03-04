package com.example.lifruk

import com.example.lifruk.connection.LogInFragment
import com.google.android.material.textfield.TextInputEditText
import org.junit.Assert
import org.junit.Test

class LogInFragmentTest {
    @Test
    fun testIsFormValidWithIncorrectData(){
        val LogIn = LogInFragment()
        LogIn.inputEmail = "123"
        LogIn.inputPwd = "12345678"
        Assert.assertTrue(!LogIn.isFormValid())
    }
    @Test
    fun testIsFormValidWithCorrectData(){
        val LogIn = LogInFragment()
        LogIn.inputEmail = "test@mail.com"
        LogIn.inputPwd = "12345678"
        Assert.assertTrue(LogIn.isFormValid())
    }
}