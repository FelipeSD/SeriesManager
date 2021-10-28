package com.seriesmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.seriesmanager.databinding.ActivityRegisterEpisodeBinding
import com.seriesmanager.model.Episode

class RegisterEpisode : AppCompatActivity() {
    private var position = -1
    private var episode: Episode? = null

    private val registerEpisodeActivity: ActivityRegisterEpisodeBinding by lazy {
        ActivityRegisterEpisodeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(registerEpisodeActivity.root)

        episode = intent.getParcelableExtra(EpisodeActivity.EXTRA_EPISODE)
        position = intent.getIntExtra(EpisodeActivity.EXTRA_POSITION, -1)

        registerEpisodeActivity.btnSave.setOnClickListener {
            save()
        }

        episode?.let {
            bindData(it)
            handleView(position)
        }
    }

    private fun handleView(position: Int){
         if(position == -1){
             for(i in 0..registerEpisodeActivity.root.childCount){
                 registerEpisodeActivity.root.getChildAt(i)?.isEnabled = false;
             }

             registerEpisodeActivity.btnSave.visibility = View.GONE;
         }else{
             registerEpisodeActivity.sequenceEt.isEnabled = false;
         }
    }

    private fun bindData(episode: Episode){
        registerEpisodeActivity.sequenceEt.setText(episode.sequence)
        registerEpisodeActivity.nameEt.setText(episode.name)
        registerEpisodeActivity.durationEt.setText(episode.duration)
        registerEpisodeActivity.watchedCk.isChecked = episode.watched
    }

    private fun save(){
        episode = Episode(
            registerEpisodeActivity.sequenceEt.text.toString(),
            registerEpisodeActivity.nameEt.text.toString(),
            registerEpisodeActivity.durationEt.text.toString(),
            registerEpisodeActivity.watchedCk.isChecked
        )

        val resultIntent = Intent()
        resultIntent.putExtra(EpisodeActivity.EXTRA_EPISODE, episode)

        if(position != -1){
            resultIntent.putExtra(MainActivity.EXTRA_POSITION, position)
        }

        setResult(RESULT_OK, resultIntent)
        finish()
    }
}