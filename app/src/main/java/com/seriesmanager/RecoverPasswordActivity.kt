package com.seriesmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.seriesmanager.databinding.ActivityRecoverPasswordBinding

class RecoverPasswordActivity : AppCompatActivity() {
    private val activityRecoverPasswordBinding: ActivityRecoverPasswordBinding by lazy {
        ActivityRecoverPasswordBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityRecoverPasswordBinding.root)
        supportActionBar?.subtitle = "Recuperar senha"

        with(activityRecoverPasswordBinding){
            enviarEmailBt.setOnClickListener {
                val email = emailEt.text.toString()
                if(email.isNotEmpty()){
                    Auth.firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener{ envio ->
                        if(envio.isSuccessful){
                            Toast.makeText(this@RecoverPasswordActivity, "E-mail de recuperação enviado", Toast.LENGTH_SHORT).show()
                        }else {
                            Toast.makeText(this@RecoverPasswordActivity, "Falha no envio do e-mail de recuperação", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}