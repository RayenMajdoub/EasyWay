package com.example.easyway.View

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NO_HISTORY
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.easyway.Model.User
import com.example.easyway.R
import com.example.easyway.Repository.SessionManager
import com.example.easyway.Repository.UserRepository
import com.example.easyway.ResponseUtils.BaseResponse
import com.example.easyway.ResponseUtils.LoginResponse
import com.example.easyway.ViewModel.LoginViewModel
import com.example.easyway.ViewModel.signupViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText





class Login : AppCompatActivity() {
    //google  api
    private lateinit var GSO: GoogleSignInOptions
    private lateinit var GSC: GoogleSignInClient

    //buttons
    private lateinit var User: User
    private lateinit var GoogleBtn: Button
    private lateinit var LoginBtn: Button
    private lateinit var SignUpBtn: TextView

    //inputs
    private lateinit var EmailInput: TextInputEditText
    private lateinit var PasswordInput: TextInputEditText
    private lateinit var UsernameInput: TextInputEditText

    private lateinit var binding: Login
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val UserRepository: UserRepository = UserRepository()

        //buttons
        GoogleBtn = findViewById(R.id.ButtonGoogle)
        LoginBtn = findViewById(R.id.ButtonLogin)
        SignUpBtn = findViewById(R.id.ButtonCreateAccount)
        //inputs
        EmailInput = findViewById(R.id.EmailInput)
        PasswordInput = findViewById(R.id.PasswordInput)
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if(acct!=null){
            updateUI()
        }
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GSO = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN) .requestEmail()
            .build()
        // Build a GoogleSignInClient with the options specified by gso.
        GSC = GoogleSignIn.getClient(this, GSO)

        //ONCLICK

        viewModel.loginResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                  //   showLoading()
                }

                is BaseResponse.Success -> {
                      //   stopLoading()
                    processLogin(it.data)
                }

                is BaseResponse.Error -> {
                    processError(it.msg)
                }
                else -> {
                    //    stopLoading()
                }
            }
        }
        GoogleBtn.setOnClickListener {
            signIn()
        }
        LoginBtn.setOnClickListener {
                viewModel.Login( EmailInput.text.toString(), PasswordInput.text.toString())
        }

        GoogleBtn.setOnClickListener {

            signIn()
        }
        SignUpBtn.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivityForResult(intent, 20)
        }
    }

    fun processLogin(data: LoginResponse?) {
        showToast("Login with success" )
        if (data != null) {

            SessionManager.saveString(this, "username" , data.data.username)
            SessionManager.saveString(this,"email", data.data.email)
            SessionManager.saveString(this,"id", data.data.id)
            showToast("Success:" + data?.data?.username)
        }
        if (!data?.token.isNullOrEmpty()) {
            data?.token?.let { SessionManager.saveAuthToken(this, it) }

        }
        updateUI()
    }

    fun processError(msg: String?) {
        showToast("Something went wrong")
    }

    fun showToast(msg: String) {
        Log.w("User Login Result " ,msg)
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun signIn() {
        val signInIntent: Intent = GSC.getSignInIntent()
        startActivityForResult(signInIntent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val SignupViewModel = signupViewModel()
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 1000) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            try {
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(data)
                task.getResult(ApiException::class.java)

                val acct = GoogleSignIn.getLastSignedInAccount(this)
                val personName = acct?.displayName
                val personEmail = acct?.email
                val token = acct?.idToken
                val res = SignupViewModel.userSignup(
                    personName.toString(),
                    personEmail.toString(),
                    "googleacount"
                )
                updateUI()
            } catch (e: ApiException) {
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT)
                    .show()

                println(e)
            }
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }



    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            Log.w("account2", "signInResult:failed code=" + account.email)
            // Signed in successfully, show authenticated UI.
            updateUI()
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("account1", "signInResult:failed code=" + e.statusCode)
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun updateUI() {
        val intent = Intent(this,Home::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(FLAG_ACTIVITY_NO_HISTORY)
        startActivity(intent)
        finish()
    }

    fun showLoading() {
        //loading.isVisible = true
    }

    fun stopLoading() {
      //  loading.isVisible = false
    }
}