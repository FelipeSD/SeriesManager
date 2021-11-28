package com.seriesmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.seriesmanager.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    private val activityAuthBinding: ActivityAuthBinding by lazy {
        ActivityAuthBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityAuthBinding.root)
        supportActionBar?.subtitle = "Autenticação"

        with(activityAuthBinding){
            cadastrarUsuarioBt.setOnClickListener {
                startActivity(Intent(this@AuthActivity, RegisterUserActivity::class.java))
            }
            recuperarSenhaBt.setOnClickListener {
                startActivity(Intent(this@AuthActivity, RecoverPasswordActivity::class.java))
            }

            entrarBt.setOnClickListener {
                val email = emailEt.text.toString()
                val password = senhaEt.text.toString()
                Auth.firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                    Toast.makeText(this@AuthActivity, "Usuário autenticado com sucesso", Toast.LENGTH_SHORT).show()
                    startMainActivity()
                }.addOnFailureListener {
                    Toast.makeText(this@AuthActivity, "Credenciais inválidas", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(Auth.firebaseAuth.currentUser != null){
            startMainActivity()
        }
    }

    private fun startMainActivity() {
        startActivity(Intent(this@AuthActivity, MainActivity::class.java))
        finish()
    }
}