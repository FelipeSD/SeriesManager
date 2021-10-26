package com.seriesmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.seriesmanager.databinding.ActivityRegisterSerieBinding

class RegisterSerie : AppCompatActivity() {
    private val registerSerieBinding: ActivityRegisterSerieBinding by lazy {
        ActivityRegisterSerieBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerSerieBinding.btnSave.setOnClickListener {
            save()
        }
    }

    private fun save(){

    }
}