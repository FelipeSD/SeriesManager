package com.seriesmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.seriesmanager.adapter.EpisodeAdapter
import com.seriesmanager.databinding.ActivityEpisodeBinding
import com.seriesmanager.model.Episode

class EpisodeActivity : AppCompatActivity() {
    companion object Extras {
        const val EXTRA_EPISODE = "EXTRA_EPISODE"
        const val EXTRA_POSITION = "EXTRA_POSITION"
    }

    private val episodeActivityBinding: ActivityEpisodeBinding by lazy {
        ActivityEpisodeBinding.inflate(layoutInflater)
    }

    private val episodeList: MutableList<Episode> = mutableListOf()

    private val episodeAdapter: EpisodeAdapter by lazy {
        EpisodeAdapter(episodeList)
    }

    private val episodeLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    private lateinit var episodeResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(episodeActivityBinding.root)

        episodeActivityBinding.btnAddEpisode.setOnClickListener{
            episodeResultLauncher.launch(Intent(this, RegisterEpisode::class.java))
        }

        episodeActivityBinding.episodeRv.layoutManager = episodeLayoutManager
        episodeActivityBinding.episodeRv.adapter = episodeAdapter

        episodeResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result -> if(result.resultCode == RESULT_OK){
                val position = result.data?.getIntExtra(EXTRA_POSITION, -1)
                val episode = result.data?.getParcelableExtra<Episode>(EXTRA_EPISODE)

                if(episode != null){
                    if(position != null && position != -1){
                        // editing
                        episodeList[position] = episode
                    }else{
                        // adding
                        episodeList.add(episode)
                    }
                    episodeAdapter.notifyDataSetChanged()
                }
            }
        }

        episodeAdapter.notifyDataSetChanged()
    }



    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = episodeAdapter.position
        val episode = episodeList[position]

        return when(item.itemId){
            R.id.viewItemMenu -> {
                val viewEpisodeIntent = Intent(this, RegisterEpisode::class.java)
                viewEpisodeIntent.putExtra(EXTRA_EPISODE, episode)
                startActivity(viewEpisodeIntent)
                true
            }
            R.id.editItemMenu -> {
                // Edit episode
                val editEpisodeIntent = Intent(this, RegisterEpisode::class.java)
                editEpisodeIntent.putExtra(EXTRA_EPISODE, episode)
                editEpisodeIntent.putExtra(EXTRA_POSITION, position)
                episodeResultLauncher.launch(editEpisodeIntent)
                true
            }
            R.id.removeItemMenu -> {
                // Remove episode
                with(AlertDialog.Builder(this)){
                    setMessage("Confirma remoção")
                    setPositiveButton("Sim"){
                        _, _ ->
                        episodeList.removeAt(position)
                        Snackbar.make(episodeActivityBinding.root, "Season ${episode.sequence} was removed", Snackbar.LENGTH_SHORT).show()
                        episodeAdapter.notifyDataSetChanged()
                    }
                    setNegativeButton("Não"){
                        _, _ ->
                        Snackbar.make(episodeActivityBinding.root, "Removal canceled", Snackbar.LENGTH_SHORT).show()
                    }
                    create()
                }.show()

                true
            }
            else -> {
                false
            }
        }
    }
}