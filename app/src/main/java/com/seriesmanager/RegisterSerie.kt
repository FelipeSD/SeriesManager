package com.seriesmanager

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.seriesmanager.databinding.ActivityRegisterSerieBinding
import com.seriesmanager.model.Serie

class RegisterSerie : AppCompatActivity() {
    private var position = -1
    private var serie: Serie? = null

    private val registerSerieActivity: ActivityRegisterSerieBinding by lazy {
        ActivityRegisterSerieBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(registerSerieActivity.root)

        serie = intent.getParcelableExtra(MainActivity.EXTRA_SERIE)
        position = intent.getIntExtra(MainActivity.EXTRA_POSITION, -1)

        registerSerieActivity.btnSave.setOnClickListener {
            save()
        }

        serie?.let {
            bindData(it)
            handleView(position)
        }
    }

    private fun handleView(position: Int){
         if(position == -1){
             for(i in 0..registerSerieActivity.root.childCount){
                 registerSerieActivity.root.getChildAt(i)?.isEnabled = false;
             }

             registerSerieActivity.btnSave.visibility = View.GONE;
         }else{
             registerSerieActivity.serieEt.isEnabled = false;
         }
    }

    private fun bindData(serie: Serie){
        registerSerieActivity.serieEt.setText(serie.name)
        registerSerieActivity.producerEt.setText(serie.producer)
        registerSerieActivity.releaseEt.setText(serie.releaseYear)
        registerSerieActivity.genreSp.setSelection(resources.getStringArray(R.array.genre).indexOf(serie.genre))
    }

    private fun save(){
        serie = Serie(
            registerSerieActivity.serieEt.text.toString(),
            registerSerieActivity.releaseEt.text.toString(),
            registerSerieActivity.producerEt.text.toString(),
            registerSerieActivity.genreSp.selectedItem.toString()
        )

        val resultIntent = Intent()
        resultIntent.putExtra(MainActivity.EXTRA_SERIE, serie)

        if(position != -1){
            resultIntent.putExtra(MainActivity.EXTRA_POSITION, position)
        }

        setResult(RESULT_OK, resultIntent)
        finish()
    }
}