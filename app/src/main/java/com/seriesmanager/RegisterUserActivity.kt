package com.seriesmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.seriesmanager.databinding.ActivityRegisterUserBinding

class RegisterUserActivity : AppCompatActivity() {
    private val activityRegisterUserBinding: ActivityRegisterUserBinding by lazy {
        ActivityRegisterUserBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityRegisterUserBinding.root)
        supportActionBar?.subtitle = "Cadastrar usuário"

        with(activityRegisterUserBinding){
            cadastrarBt.setOnClickListener {
                val email = emailEt.text.toString()
                val password = senhaEt.text.toString()
                val repeatPassword = repetirSenhaEt.text.toString()

                if(password == repeatPassword){
                    // Register
                    Auth.firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                        Toast.makeText(this@RegisterUserActivity, "Usuário foi cadastrado", Toast.LENGTH_SHORT).show()
                        finish()
                    }.addOnFailureListener {
                        Toast.makeText(this@RegisterUserActivity, "Falha no cadastro", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this@RegisterUserActivity, "Senhas não coincidem", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}