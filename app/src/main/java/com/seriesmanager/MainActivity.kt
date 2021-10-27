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

    // Data source
    private val seriesList: MutableList<Serie> = mutableListOf()
/*
    by lazy {
        chamar controller
        mutableListOf(Serie("teste1", 2020, "Netflix", "drama"))
    }
*/

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
                    }else{
                        // adding
                        seriesList.add(serie)
                    }
                    serieAdapter.notifyDataSetChanged()
                }
            }
        }

        serieActivityBinding.btnAddSerie.setOnClickListener{
            serieResultLauncher.launch(Intent(this, RegisterSerie::class.java))
        }

//        seriesList.add(Serie("teste", "2020", "Netflix", "Drama"))
//        serieAdapter.notifyDataSetChanged()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = serieAdapter.position
        val serie = seriesList[position]

        return when(item.itemId){
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
        val viewSerieIntent = Intent(this, RegisterSerie::class.java)
        viewSerieIntent.putExtra(EXTRA_SERIE, serie)
        startActivity(viewSerieIntent)
    }
}