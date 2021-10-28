package com.seriesmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.seriesmanager.databinding.ActivityRegisterSeasonBinding
import com.seriesmanager.model.Season

class RegisterSeason : AppCompatActivity() {
    private var position = -1
    private var season: Season? = null

    private val registerSeasonActivity: ActivityRegisterSeasonBinding by lazy {
        ActivityRegisterSeasonBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(registerSeasonActivity.root)

        season = intent.getParcelableExtra(SeasonActivity.EXTRA_SEASON)
        position = intent.getIntExtra(SeasonActivity.EXTRA_POSITION, -1)

        registerSeasonActivity.btnSave.setOnClickListener {
            save()
        }

        season?.let {
            bindData(it)
            handleView(position)
        }
    }

    private fun handleView(position: Int){
         if(position == -1){
             for(i in 0..registerSeasonActivity.root.childCount){
                 registerSeasonActivity.root.getChildAt(i)?.isEnabled = false;
             }

             registerSeasonActivity.btnSave.visibility = View.GONE;
         }else{
             registerSeasonActivity.sequenceEt.isEnabled = false;
         }
    }

    private fun bindData(season: Season){
        registerSeasonActivity.sequenceEt.setText(season.sequence)
        registerSeasonActivity.releaseEt.setText(season.releaseYear)
        registerSeasonActivity.episodesEt.setText(season.numberEpisodes)
    }

    private fun save(){
        season = Season(
            registerSeasonActivity.sequenceEt.text.toString(),
            registerSeasonActivity.releaseEt.text.toString(),
            registerSeasonActivity.episodesEt.text.toString(),
        )
        val resultIntent = Intent()
        resultIntent.putExtra(SeasonActivity.EXTRA_SEASON, season)

        if(position != -1){
            resultIntent.putExtra(MainActivity.EXTRA_POSITION, position)
        }

        setResult(RESULT_OK, resultIntent)
        finish()
    }
}