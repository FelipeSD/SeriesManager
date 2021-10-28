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
import com.seriesmanager.adapter.OnItemClickListener
import com.seriesmanager.adapter.SeasonAdapter
import com.seriesmanager.databinding.ActivitySeasonBinding
import com.seriesmanager.model.Season

class SeasonActivity : AppCompatActivity(), OnItemClickListener {
    companion object Extras {
        const val EXTRA_SEASON = "EXTRA_SEASON"
        const val EXTRA_POSITION = "EXTRA_POSITION"
    }

    private val seasonActivityBinding: ActivitySeasonBinding by lazy {
        ActivitySeasonBinding.inflate(layoutInflater)
    }

    private val seasonList: MutableList<Season> = mutableListOf()

    private val seasonAdapter: SeasonAdapter by lazy {
        SeasonAdapter(this, seasonList)
    }

    private val seasonLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    private lateinit var seasonResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(seasonActivityBinding.root)

        seasonActivityBinding.btnAddSeason.setOnClickListener{
            Snackbar.make(seasonActivityBinding.root, "Add temporada", Snackbar.LENGTH_SHORT).show()
            seasonResultLauncher.launch(Intent(this, RegisterSeason::class.java))
        }

        seasonActivityBinding.seasonRv.layoutManager = seasonLayoutManager
        seasonActivityBinding.seasonRv.adapter = seasonAdapter

        seasonResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result -> if(result.resultCode == RESULT_OK){
                val position = result.data?.getIntExtra(EXTRA_POSITION, -1)
                val season = result.data?.getParcelableExtra<Season>(EXTRA_SEASON)

                if(season != null){
                    if(position != null && position != -1){
                        // editing
                        seasonList[position] = season
                    }else{
                        // adding
                        seasonList.add(season)
                    }
                    seasonAdapter.notifyDataSetChanged()
                }
            }
        }

        seasonAdapter.notifyDataSetChanged()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = seasonAdapter.position
        val season = seasonList[position]

        return when(item.itemId){
            R.id.viewItemMenu -> {
                val viewSerieIntent = Intent(this, RegisterSeason::class.java)
                viewSerieIntent.putExtra(EXTRA_SEASON, season)
                startActivity(viewSerieIntent)
                true
            }
            R.id.editItemMenu -> {
                // Edit season
                val editSerieIntent = Intent(this, RegisterSeason::class.java)
                editSerieIntent.putExtra(EXTRA_SEASON, season)
                editSerieIntent.putExtra(EXTRA_POSITION, position)
                seasonResultLauncher.launch(editSerieIntent)
                true
            }
            R.id.removeItemMenu -> {
                // Remove season
                with(AlertDialog.Builder(this)){
                    setMessage("Confirma remoção")
                    setPositiveButton("Sim"){
                        _, _ ->
                        seasonList.removeAt(position)
                        Snackbar.make(seasonActivityBinding.root, "Season ${season.sequence} was removed", Snackbar.LENGTH_SHORT).show()
                        seasonAdapter.notifyDataSetChanged()
                    }
                    setNegativeButton("Não"){
                        _, _ ->
                        Snackbar.make(seasonActivityBinding.root, "Removal canceled", Snackbar.LENGTH_SHORT).show()
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

    override fun onItemClick(position: Int) {
        val season = seasonList[position]
//        val seasonActivityIntent = Intent(this, SeasonActivity::class.java)
//        seasonActivityIntent.putExtra(EXTRA_SEASON, season)
//        startActivity(seasonActivityIntent)
    }
}