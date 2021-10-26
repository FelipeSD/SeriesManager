package com.seriesmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.seriesmanager.adapter.OnItemClickListener
import com.seriesmanager.adapter.SerieAdapter
import com.seriesmanager.databinding.ActivityMainBinding
import com.seriesmanager.model.Serie

class MainActivity : AppCompatActivity(), OnItemClickListener {
    companion object Extras {
        const val EXTRA_SERIE = "EXTRA_SERIE"
        const val EXTRA_POSITION = "EXTRA_POSITION"
    }

    private val serieMainBinding : ActivityMainBinding by lazy {
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
        setContentView(serieMainBinding.root)

        serieMainBinding.seriesRv.adapter = serieAdapter
        serieMainBinding.seriesRv.layoutManager = serieLayoutManager

        serieResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result -> if(result.resultCode == RESULT_OK){
                val position = result.data?.getIntExtra(EXTRA_POSITION, -1)
                val serie = result.data?.getParcelableExtra<Serie>(EXTRA_SERIE)
            }
        }

        serieMainBinding.btnAddSerie.setOnClickListener{
            serieResultLauncher.launch(Intent(this, RegisterSerie::class.java))
        }

        seriesList.add(Serie("teste", 2020, "Netflix", "drama"))
        serieAdapter.notifyDataSetChanged()
    }

    override fun onItemClick(position: Int) {
        val serie = seriesList[position]
    }
}