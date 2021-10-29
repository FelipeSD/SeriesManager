package com.seriesmanager.controller

import com.seriesmanager.dao.SerieSqlite
import com.seriesmanager.MainActivity
import com.seriesmanager.model.Serie

class SerieController(mainActivity: MainActivity) {
    private val serieDAO = SerieSqlite(mainActivity)

    fun createSerie(serie: Serie) = serieDAO.createSerie(serie)
    fun findSerie(name: String) = serieDAO.findSerie(name)
    fun getSeries() = serieDAO.getSeries()
    fun updateSerie(serie: Serie) = serieDAO.updateSerie(serie)
    fun deleteSerie(name: String) = serieDAO.deleteSerie(name)
}