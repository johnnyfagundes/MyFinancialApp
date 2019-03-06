package br.udemy.mobile.androidfirebase.Activities

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import br.udemy.mobile.androidfirebase.R
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {

    override fun onConnectionFailed(p0: ConnectionResult) {
        Toast.makeText(this, "" + p0.errorMessage, Toast.LENGTH_SHORT).show()
    }


    companion object {
        private val PERMISSION_CODE = 9999
    }

    lateinit var mGoogleApiClient: GoogleApiClient
//    lateinit var firebaseAuth: FirebaseAuth

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == PERMISSION_CODE) {

            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {

                val account = result.signInAccount
                val idToken = account?.idToken
//
//                Toast.makeText(applicationContext, "Sucesso $idToken",
//                        Toast.LENGTH_SHORT).show()

                val credential = GoogleAuthProvider.getCredential(idToken, null)

                firebaseAuthWithGoogle(credential)

            } else {

                Log.d("EDMT_ERROR", "Login Unsuccessful")
                Toast.makeText(applicationContext, "Sem Sucesso",
                        Toast.LENGTH_SHORT).show()

            }
        } else {
            Toast.makeText(applicationContext, "Códigos não bateram",
                    Toast.LENGTH_SHORT).show()
        }

    }

    private fun firebaseAuthWithGoogle(credential: AuthCredential?) {

        val mAuth = FirebaseAuth.getInstance()

        mAuth.signInWithCredential(credential!!)
                .addOnSuccessListener { authResult ->
                    val loggedEmail = authResult.user.email
                    val loggedActivity = Intent(this@LoginActivity, Home::class.java)
                    loggedActivity.putExtra("email", loggedEmail)
                    startActivity(loggedActivity)

                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "" + e.message, Toast.LENGTH_SHORT).show()
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        configureGoogleClient()

        btnLogin.setOnClickListener {
            login(edtEmail.text.toString(), edtSenha.text.toString(), this@LoginActivity)
        }

        btnTelaRedefinir.setOnClickListener {
            startActivity(Intent(applicationContext, ForgotPassword::class.java))
        }

        btnCreate.setOnClickListener {
            startActivity(Intent(this, CreateUser::class.java))
        }

//        btnLoginByGoogle.setOnClickListener {
//            signIn()
//        }

    }

    private fun signIn() {
        val intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(intent, PERMISSION_CODE)
    }

    private fun login(email: String, password: String, activity: Activity) {
        if (!email.isEmpty() && !password.isEmpty()) {
            val mAuth = FirebaseAuth.getInstance()
            mAuth?.signInWithEmailAndPassword(email, password)
                    ?.addOnCompleteListener(activity) { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(applicationContext, Home::class.java))
                        } else {
                            Toast.makeText(applicationContext, "Autenticação falhou",
                                    Toast.LENGTH_SHORT).show()
                        }

                    }
        } else {
            Toast.makeText(applicationContext, "Indique o login e senha",
                    Toast.LENGTH_SHORT).show()
        }

    }

    private fun configureGoogleClient() {

        val options: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                .build()
        mGoogleApiClient.connect()
    }
}
