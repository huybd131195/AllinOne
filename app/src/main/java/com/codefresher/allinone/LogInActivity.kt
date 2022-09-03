package com.codefresher.allinone

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import com.codefresher.allinone.databinding.ActivityLogInBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LogInActivity : AppCompatActivity() {
    private lateinit var loginBinding: ActivityLogInBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        window.statusBarColor = this.getColor(R.color.a2)
        auth = FirebaseAuth.getInstance()

        logInTransparent()
        logIn()
        signUp()


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)


        loginBinding.btnGoogle.setOnClickListener {
            signInGoogle()
        }
    }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleResults(task)
            }
        }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            if (account != null) {
                updateUI(account)
            }
        } else {
            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("email", account.email)
                intent.putExtra("name", account.displayName)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

            }
        }
    }


    private fun logInTransparent() {
        loginBinding.tvSignUp.setOnClickListener {
            loginBinding.tvSignUp.background =
                ResourcesCompat.getDrawable(resources, R.drawable.switch_trcks, null)
            loginBinding.tvSignUp.setTextColor(resources.getColor(R.color.white, null))
            loginBinding.tvLogIn.background = null
            loginBinding.signUpLayout.visibility = View.VISIBLE
            loginBinding.logInLayout.visibility = View.GONE
            loginBinding.tvLogIn.setTextColor(resources.getColor(R.color.a1, null))
            loginBinding.btnLogIn.visibility = View.GONE
            loginBinding.btnSignUp.visibility = View.VISIBLE
        }
        loginBinding.tvLogIn.setOnClickListener {
            loginBinding.tvLogIn.background =
                ResourcesCompat.getDrawable(resources, R.drawable.switch_trcks, null)
            loginBinding.tvLogIn.setTextColor(resources.getColor(R.color.white, null))
            loginBinding.tvSignUp.background = null
            loginBinding.logInLayout.visibility = View.VISIBLE
            loginBinding.signUpLayout.visibility = View.GONE
            loginBinding.tvSignUp.setTextColor(resources.getColor(R.color.a1, null))
            loginBinding.btnSignUp.visibility = View.GONE
            loginBinding.btnLogIn.visibility = View.VISIBLE

        }

    }

    private fun logIn() {
        loginBinding.btnLogIn.setOnClickListener {
            val email = loginBinding.edtEmail.text.toString()
            val pass = loginBinding.edtPassword.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signUp() {

        loginBinding.btnSignUp.setOnClickListener {
            val emailSignUp = loginBinding.edtEmailSignUp.text.toString()
            val passSignUp = loginBinding.edtPassSignUp.text.toString()
            val rePass = loginBinding.edtRePass.text.toString()



            if (emailSignUp.isNotEmpty() && passSignUp.isNotEmpty() && rePass.isNotEmpty()) {
                if (passSignUp == rePass) {
                    auth.createUserWithEmailAndPassword(emailSignUp, passSignUp)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {

                                Toast.makeText(
                                    this,
                                    "Sign Up successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    this,
                                    task.exception.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    Toast.makeText(
                        this,
                        "Password is not matching",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    this,
                    "Empty Fields Are not Allowed !!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        val user = auth.currentUser

        if (user != null) {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}