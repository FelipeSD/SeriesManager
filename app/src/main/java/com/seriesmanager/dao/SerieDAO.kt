package com.seriesmanager.dao

import com.seriesmanager.model.Serie

interface SerieDAO {
    fun createSerie(serie: Serie): Long
    fun findSerie(name: String): Serie
    fun getSeries(): MutableList<Serie>
    fun updateSerie(serie: Serie): Int
    fun deleteSerie(name: String): Int
}