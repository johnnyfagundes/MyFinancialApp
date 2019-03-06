package br.udemy.mobile.androidfirebase.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.udemy.mobile.androidfirebase.Classes.Profile
import br.udemy.mobile.androidfirebase.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_cadastro_usuario.*
import java.lang.Exception

class CreateUser : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_usuario)

//        load()

        btnCadastro.setOnClickListener { save() }

        btnCancel.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }
    }

    private fun load() {
        val mAuth = FirebaseAuth.getInstance()
        val email = mAuth.currentUser?.email
        txtEmail.setText(email)
    }

    private fun save() {
        val address = txtAddress.text.toString()
        val name = txtName.text.toString()
        val email = txtEmail.text.toString()
        val password = txtPassword.text.toString()

        if (address.isEmpty() || name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Preencher todos os campos", Toast.LENGTH_LONG).show()
            return
        }

        if (!createUser(email, password)) {
            return
        }

        val database = FirebaseDatabase.getInstance()
        val profileRef = database.getReference("profile")

        val profile = Profile(name, email, address)

        profileRef.push().setValue(profile)

        Toast.makeText(applicationContext, "Usuário $name salvo com sucesso!",
                Toast.LENGTH_LONG).show()

        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun createUser(email: String, password: String): Boolean {
        val mAuth = FirebaseAuth.getInstance()
        try {
            mAuth.createUserWithEmailAndPassword(email, password)

        } catch (e: Exception) {
            return false
        }
        return true
    }

}
