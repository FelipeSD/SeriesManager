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
import com.seriesmanager.adapter.SerieAdapter
import com.seriesmanager.controller.SerieController
import com.seriesmanager.databinding.ActivityMainBinding
import com.seriesmanager.model.Serie

class MainActivity : AppCompatActivity(), OnItemClickListener {
    companion object Extras {
        const val EXTRA_SERIE = "EXTRA_SERIE"
        const val EXTRA_POSITION = "EXTRA_POSITION"
    }

    private val serieActivityBinding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // Controller
    private val serieController: SerieController by lazy {
        SerieController(this)
    }

    // Data source
    private val seriesList: MutableList<Serie> by lazy {
        serieController.getSeries()
    }

    // Adapter
    private val serieAdapter: SerieAdapter by lazy {
        SerieAdapter(this, seriesList)
    }

    // Layout Manager
    private val serieLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    // VIEW REGISTER SERIE
    private lateinit var serieResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(serieActivityBinding.root)

        serieActivityBinding.seriesRv.adapter = serieAdapter
        serieActivityBinding.seriesRv.layoutManager = serieLayoutManager

        serieResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result -> if(result.resultCode == RESULT_OK){
                val position = result.data?.getIntExtra(EXTRA_POSITION, -1)
                val serie = result.data?.getParcelableExtra<Serie>(EXTRA_SERIE)

                if(serie != null){
                    if(position != null && position != -1){
                        // editing
                        seriesList[position] = serie
                        serieController.updateSerie(serie)
                    }else{
                        // adding
                        seriesList.add(serie)
                        serieController.createSerie(serie)
                    }
                    serieAdapter.notifyDataSetChanged()
                }
            }
        }

        serieActivityBinding.btnAddSerie.setOnClickListener{
            serieResultLauncher.launch(Intent(this, RegisterSerie::class.java))
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = serieAdapter.position
        val serie = seriesList[position]

        return when(item.itemId){
            R.id.viewItemMenu -> {
                val viewSerieIntent = Intent(this, RegisterSerie::class.java)
                viewSerieIntent.putExtra(EXTRA_SERIE, serie)
                startActivity(viewSerieIntent)
                true
            }
            R.id.editItemMenu -> {
                // Edit serie
                val editSerieIntent = Intent(this, RegisterSerie::class.java)
                editSerieIntent.putExtra(EXTRA_SERIE, serie)
                editSerieIntent.putExtra(EXTRA_POSITION, position)
                serieResultLauncher.launch(editSerieIntent)
                true
            }
            R.id.removeItemMenu -> {
                // Remove serie
                with(AlertDialog.Builder(this)){
                    setMessage("Confirma remoção")
                    setPositiveButton("Sim"){
                        _, _ ->
                        seriesList.removeAt(position)
                        serieController.deleteSerie(serie.name)
                        serieAdapter.notifyDataSetChanged()
                        Snackbar.make(serieActivityBinding.root, "${serie.name} was removed", Snackbar.LENGTH_SHORT).show()
                    }
                    setNegativeButton("Não"){
                        _, _ ->
                        Snackbar.make(serieActivityBinding.root, "Removal canceled", Snackbar.LENGTH_SHORT).show()
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
        val serie = seriesList[position]
        val seasonActivityIntent = Intent(this, SeasonActivity::class.java)
        seasonActivityIntent.putExtra(EXTRA_SERIE, serie)
        startActivity(seasonActivityIntent)
    }
}