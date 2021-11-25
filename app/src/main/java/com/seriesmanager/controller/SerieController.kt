package com.seriesmanager.controller

import com.seriesmanager.dao.serie.SerieSqlite
import com.seriesmanager.MainActivity
import com.seriesmanager.dao.serie.SerieFirebase
import com.seriesmanager.model.Serie

class SerieController(mainActivity: MainActivity) {
    private val serieDAO = SerieSqlite(mainActivity)
    private val serieFirebase = SerieFirebase()

    fun createSerie(serie: Serie) = serieFirebase.createSerie(serie)
    fun findSerie(name: String) = serieFirebase.findSerie(name)
    fun getSeries() = serieFirebase.getSeries()
    fun updateSerie(serie: Serie) = serieFirebase.updateSerie(serie)
    fun deleteSerie(name: String) = serieFirebase.deleteSerie(name)
}