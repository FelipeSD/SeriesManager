package com.seriesmanager.controller

import com.seriesmanager.dao.SerieSqlite
import com.seriesmanager.MainActivity
import com.seriesmanager.model.Serie

class SerieController(mainActivity: MainActivity) {
    private val serieDao = SerieSqlite(mainActivity)

    fun createSerie(serie: Serie) = serieDao.createSerie(serie)
    fun findSerie(name: String) = serieDao.findSerie(name)
    fun getSeries() = serieDao.getSeries()
    fun updateSerie(serie: Serie) = serieDao.updateSerie(serie)
    fun deleteSerie(name: String) = serieDao.deleteSerie(name)
}