package br.udemy.mobile.androidfirebase.Activities

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.udemy.mobile.androidfirebase.R
import kotlinx.android.synthetic.main.activity_forget.*
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth


class ForgotPassword : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget)

        btnReset.setOnClickListener {
            redefinir(txtEmail.text.toString(), this@ForgotPassword)
        }

        btnCancel.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }
    }

    private fun redefinir(email: String, activity: Activity) {
        val mAuth = FirebaseAuth.getInstance()
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(applicationContext, "Recuperação de acesso iniciada. Email enviado!",
                                Toast.LENGTH_SHORT).show()
                        startActivity(Intent(applicationContext, LoginActivity::class.java))
                    } else {
                        Toast.makeText(applicationContext, "Erro ao resetar senha",
                                Toast.LENGTH_SHORT).show()
                    }

                }
    }
}
