package com.example.easyway.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns.EMAIL_ADDRESS
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.example.easyway.Model.User
import com.example.easyway.R
import com.example.easyway.Repository.UserRepository
import com.example.easyway.ViewModel.LoginViewModel
import com.example.easyway.ViewModel.signupViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

private lateinit var User: User
private lateinit var GoogleBtn: Button
private lateinit var FacebookBtn: Button
private lateinit var ButtonSignup: Button
private lateinit var LoginBtn: TextView


//inputs
private lateinit var EmailInput: TextInputEditText
private lateinit var PasswordInput: TextInputEditText
private lateinit var PasswordConfirm: TextInputEditText
private lateinit var UsernameInput: TextInputEditText
private lateinit var outlinedUsername: TextInputLayout
private lateinit var outlinedEmail: TextInputLayout
private lateinit var outlinedPassword: TextInputLayout
private lateinit var outlinedCpassword: TextInputLayout

class SignUp : AppCompatActivity() {
    private val viewModel by viewModels<signupViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        UsernameInput = findViewById(R.id.UsernameInput)
        EmailInput = findViewById(R.id.EmailInput)
        PasswordInput = findViewById(R.id.PasswordInput)
        PasswordConfirm = findViewById(R.id.PasswordConfirmInput)
        ButtonSignup = findViewById(R.id.ButtonSignup)
        LoginBtn = findViewById(R.id.ButtonSignIn)

        LoginBtn.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivityForResult(intent, 10)
        }

        ButtonSignup.setOnClickListener {
            if (validate(
                    EmailInput.text.toString(),
                    UsernameInput.text.toString(),
                    PasswordInput.text.toString(),
                    PasswordConfirm.text.toString()
                )
            ) {


                val res = viewModel.userSignup(
                    UsernameInput.text.toString(), EmailInput.text.toString(),
                    PasswordInput.text.toString()
                )


                viewModel.SignuResult.observe(this) {
                    toast(it)
                    println(it)
                }

            }

        }
    }

    fun validate(email: String, fullname: String, password: String, cpassword: String): Boolean {
        outlinedUsername = findViewById(R.id.outlined_Username)
        outlinedEmail = findViewById(R.id.outlined_email)
        outlinedPassword = findViewById(R.id.outlined_password)
        outlinedCpassword = findViewById(R.id.outlined_ConfirmPassword)
        var er1: Boolean = false
        var er2: Boolean = false
        var er3: Boolean = false
        if (fullname.isEmpty()) {
            outlinedUsername.error = "Must not be empty!"

            er1 = false
        } else {
            outlinedUsername.error = null
            er1 = true
        }
        if (email.isEmpty()) {
            outlinedEmail.error = "Must not be empty!"
            er2 = false
        } else if (!EMAIL_ADDRESS.matcher(email).matches()) {
            outlinedEmail.error = "Check your email!"
            er2 = false
        } else {
            outlinedEmail.error = null
            er2 = true
        }

        if (password.isEmpty()) {
            outlinedPassword.error = "Must not be empty!"
            er3 = false

        } else if (password != cpassword) {
            outlinedPassword.error = "Password is not matching"
            outlinedCpassword.error = "Password is not matching"
            er3 = false
        } else {
            outlinedPassword.error = null
            outlinedCpassword.error = null
            er3 = true
        }

        if (er1 && er2 && er3)
            return true
        else return false
    }

    fun toast(res: Int) {

        if (res == 4) {

            // progBar.visibility = View.VISIBLE

            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        }
        if (res == 1) {
            //   progBar.visibility = View.INVISIBLE
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            Toast.makeText(this, "Account Created", Toast.LENGTH_SHORT).show()

        }
        if (res == 2) {
            Toast.makeText(this, "acc", Toast.LENGTH_SHORT).show()
            // progBar.visibility = View.INVISIBLE
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
        if (res == 3) {
            Toast.makeText(this, "connection Error", Toast.LENGTH_SHORT).show()
            //  progBar.visibility = View.INVISIBLE
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }

    }
}